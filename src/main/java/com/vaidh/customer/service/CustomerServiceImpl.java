package com.vaidh.customer.service;

import com.vaidh.customer.constants.ResponseMessage;
import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.dto.ProductHistoryDTO;
import com.vaidh.customer.dto.request.ModifyUserRequest;
import com.vaidh.customer.dto.response.AddPrescriptionResponse;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.dto.response.HistoryResponse;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.message.OrderCreateMessage;
import com.vaidh.customer.message.PaymentMessage;
import com.vaidh.customer.message.PlacedOrderMessage;
import com.vaidh.customer.message.UserMessage;
import com.vaidh.customer.model.customer.UserEntity;
import com.vaidh.customer.model.enums.FreshCartStatus;
import com.vaidh.customer.model.enums.OrderStatus;
import com.vaidh.customer.model.inventory.*;
import com.vaidh.customer.repository.*;
import com.vaidh.customer.util.StringUtil;
import com.vaidh.customer.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.vaidh.customer.constants.ResponseMessage.SUCCESSFULLY_MODIFIED;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final String FRESH_CART_REF_ID_JOINER = "_";
    private Logger logger = Logger.getLogger(String.valueOf(CustomerServiceImpl.class));
    @Autowired
    ProductRepository productRepository;

    @Autowired
    FreshCartRepository freshCartRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModifiedCartItemRepository modifiedCartItemRepository;

    @Autowired
    FireBaseStorageServiceImpl fireBaseStorageService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    FreshCartItemRepository freshCartItemRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllActiveProducts();
    }

   /* @Override
    public CommonMessageResponse addItemToCart(Long productId, Integer quantity) throws ModuleException {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                freshCartItemRepository.save(new FreshCartItem(getFreshCartId(), productId, quantity, product.get().getPrice()));
                return new CommonMessageResponse(ResponseMessage.SUCCESSFULLY_ADDED);
            } else {
                throw new ModuleException("No product exists with id " + productId);
            }
        } catch (Exception e) {
            throw new ModuleException(e.getMessage());
        }
    }

    @Override
    public CommonMessageResponse addItemToCart(Map<Long, Integer> items) throws ModuleException {
        try {
            String freshCartId = getFreshCartId();
            List<Product> products = productRepository.findAllById(items.keySet());
            Map<Long, Double> productKeyWisePrice = products.stream().collect(Collectors.toMap(Product::getProductId,
                    Product::getPrice, (x,y)->x));
            List<FreshCartItem> freshCartItems = Optional.ofNullable(items.entrySet()).orElse(new HashSet<>()).
                    stream().map(item -> new FreshCartItem(freshCartId, item.getKey(), item.getValue(),
                    productKeyWisePrice.get(item.getKey())))
                    .collect(Collectors.toList());
            freshCartItemRepository.saveAll(freshCartItems);
            return new CommonMessageResponse(ResponseMessage.SUCCESSFULLY_ADDED);
        } catch (Exception e) {
            throw new ModuleException(e.getMessage());
        }
    }*/

    @Override
    public AddPrescriptionResponse addPrescriptionToCart(MultipartFile file) throws ModuleException {
        try {
            String url = fileStorageService.save(file);
            //freshCartItemRepository.save(new FreshCartItem(getFreshCartId(), url));
            //create new order and sent cart reference to admin
            String freshCartId = getFreshCartId();
            Order order = new Order(freshCartId, authenticationService.getCurrentUserName(), new Date(), url);
            orderRepository.save(order);

            sendFirebaseCreateOrderMessage(freshCartId, url);
            return new AddPrescriptionResponse(freshCartId);
        } catch (Exception e) {
            throw new ModuleException(e.getMessage());
        }
    }

    private void sendFirebaseCreateOrderMessage(String freshCartId, String imageUrl) {
        Optional<UserEntity> user = userRepository.findByUsername(authenticationService.getCurrentUserName());
        if (user.isPresent()) {
            UserMessage userMessage = UserUtil.getUserMessage(user.get());
            fireBaseStorageService.sendMessage("orders/"+freshCartId, new OrderCreateMessage(imageUrl, userMessage, OrderStatus.CREATED));
        }
    }

    @Override
    public CommonMessageResponse placeOrder() throws ModuleException {
        return null;
        /*FreshCart currentFreshCart = freshCartRepository.findByUsername(authenticationService.getCurrentUserName()).get();
        if (currentFreshCart == null || currentFreshCart.getStatus() != FreshCartStatus.ACTIVE) {
            throw new ModuleException("No Active Cart");
        }
        //save new order entry to db
        try {
            orderRepository.save(new Order(currentFreshCart.getCartReferenceId(), authenticationService.getCurrentUserName(),
                    new Date()));
        } catch (DataIntegrityViolationException e) {logger.warning("Order already exist with fresh cart id " + currentFreshCart.getCartReferenceId());}
        //active cart -> deactive
        deActivateCurrentFreshCart(currentFreshCart);
        //fetching cart item details
        List<FreshCartItem> freshCartItems = freshCartItemRepository.findByFreshCartReferenceId
                (currentFreshCart.getCartReferenceId());
        //sending messaging to firebase
        fireBaseStorageService.saveTestDate(currentFreshCart.getCartReferenceId(), new OrderCreateMessage(
                authenticationService.getCurrentUserName(), freshCartItems.stream().filter(freshCartItem ->
                freshCartItem.getProductId() != null).collect(Collectors.toMap(freshCartItem -> freshCartItem.getProductId().toString(),
                FreshCartItem::getQuantity, (x,y)-> x))
        , freshCartItems.stream().filter(freshCartItem -> freshCartItem.getPrescribedImage() != null &&
                !freshCartItem.getPrescribedImage().isEmpty()).map(freshCartItem ->
                freshCartItem.getPrescribedImage()).collect(Collectors.toList())));
        return new CommonMessageResponse(ResponseMessage.SUCCESSFULLY_PLACED_ORDER + " :: " + currentFreshCart.getCartReferenceId());*/
    }

    @Override
    public List<CommonResults> getHistories() {
        try {
            List<Order> orders = orderRepository.findOrdersByUser(authenticationService.getCurrentUserName());
            List<CommonResults> histories = new ArrayList<>();
            for (Order order : orders) {
                List<FreshCartItem> freshCartItems = freshCartItemRepository.
                        findByFreshCartReferenceId(order.getFreshCartReferenceId());
                List<ProductHistoryDTO> products = new ArrayList<>();
                if (freshCartItems != null && !freshCartItems.isEmpty()) {
                    List<Product> productList = productRepository.
                            findAllById(freshCartItems.stream().map(freshCartItem ->
                                    freshCartItem.getProductId()).collect(Collectors.toList()));
                    Map<Long, Product> productIdWiseProducts = productList.stream().collect(
                            Collectors.toMap(Product::getProductId, p -> p, (x, y) -> x));

                    for (FreshCartItem freshCartItem : freshCartItems) {
                        ProductHistoryDTO product = new ProductHistoryDTO();
                        product.setName(productIdWiseProducts.get(freshCartItem.getProductId()).getName());
                        product.setCompanyName(productIdWiseProducts.get(freshCartItem.getProductId()).getCompanyName());
                        product.setDescription(productIdWiseProducts.get(freshCartItem.getProductId()).getDescription());
                        product.setPrice(freshCartItem.getPrice());
                        product.setImageURL(productIdWiseProducts.get(freshCartItem.getProductId()).getImageUrl());
                        product.setQuantity(freshCartItem.getQuantity());

                        products.add(product);
                    }
                }
                Payment payment = order.getPayment();
                HistoryResponse historyResponse = new HistoryResponse(order.getOrderCreatedTime(), products,
                        order.getOrderStatus(), payment != null ? payment.getTotalAmount() != null ? payment.getTotalAmount() : 0.0 : 0.0, payment != null ?
                        payment.getOfferAmount() != null ? payment.getOfferAmount() : 0.0  : 0.0,
                        payment != null ? payment.getNetAmount() != null ? payment.getNetAmount(): 0.0 : 0.0, order.getPrescribedImageUrl());
                histories.add(historyResponse);
            }
            return histories;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CommonMessageResponse modifyUserRequest(ModifyUserRequest modifyUserRequest) throws ModuleException {
        if (modifyUserRequest != null) {
            String username = authenticationService.getCurrentUserName();
            Optional<UserEntity> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                UserEntity user = userOpt.get();
                if (!StringUtil.isNullOrEmpty(modifyUserRequest.getAddress())) {
                    user.setAddress(modifyUserRequest.getAddress());
                }
                if (!StringUtil.isNullOrEmpty(modifyUserRequest.getName())) {
                    user.setName(modifyUserRequest.getName());
                }
                userRepository.save(user);
                return new CommonMessageResponse(SUCCESSFULLY_MODIFIED);
            }
        }
        throw new ModuleException("Bad Strings");
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findProductsByName(name.toLowerCase());
    }

    @Override
    public AddPrescriptionResponse getLastAddedOrder() throws ModuleException {
        Optional<Order> orderEnt = orderRepository.findOrdersByUserAndStatus(authenticationService.
                getCurrentUserName(), OrderStatus.CREATED.toString());
        if (orderEnt.isPresent()) {
            return new AddPrescriptionResponse(orderEnt.get().getFreshCartReferenceId());
        }
        throw new ModuleException("No order found");
    }

    private void deActivateCurrentFreshCart(FreshCart freshCart) {
        freshCart.setStatus(FreshCartStatus.DE_ACTIVE);
        freshCartRepository.save(freshCart);
    }


    private String getFreshCartId() {
        String username = authenticationService.getCurrentUserName();
        return username + FRESH_CART_REF_ID_JOINER + System.currentTimeMillis();
        /*Optional<FreshCart> freshCart = freshCartRepository.findByUsername(username);
        //check there is any shopping cart for this user
        if (!freshCart.isPresent()) {
            String freshCartReferenceId = username + FRESH_CART_REF_ID_JOINER + System.currentTimeMillis();
            freshCartRepository.save(new FreshCart(username, FreshCartStatus.ACTIVE, freshCartReferenceId));
            return freshCartReferenceId;
        } else {
            if (freshCart.get().getStatus().equals(FreshCartStatus.ACTIVE.toString())) {
                return freshCart.get().getCartReferenceId();
            } else {
                String freshCartRefId = username + FRESH_CART_REF_ID_JOINER + System.currentTimeMillis();
                FreshCart existFreshCart = freshCart.get();
                existFreshCart.setStatus(FreshCartStatus.ACTIVE);
                existFreshCart.setCartReferenceId(freshCartRefId);
                freshCartRepository.save(existFreshCart);
                return freshCartRefId;
            }
        }*/
    }

    @Override
    public AddPrescriptionResponse addItemToCartAndPlaceOrder(Map<Long, Integer> items) throws ModuleException {
        try {
            String freshCartId = getFreshCartId();
            List<Product> products = productRepository.findAllById(items.keySet());
            Map<Long, Double> productKeyWisePrice = products.stream().collect(Collectors.toMap(Product::getProductId,
                    Product::getPrice, (x,y)->x));
            List<FreshCartItem> freshCartItems = Optional.ofNullable(items.entrySet()).orElse(new HashSet<>()).
                    stream().map(item -> new FreshCartItem(freshCartId, item.getKey(), item.getValue(),
                    productKeyWisePrice.get(item.getKey())))
                    .collect(Collectors.toList());
            freshCartItemRepository.saveAll(freshCartItems);

            Order order = new Order(freshCartId, authenticationService.getCurrentUserName(), new Date(), null);
            Double tot = 0.0;
            if (order != null) {
                order.setOrderStatus(OrderStatus.CREATED);

                tot = paymentService.calculateTotalPaymentOfOrder(freshCartItems);
                Payment payment = new Payment();
                payment.setTotalAmount(tot);
                payment.setNetAmount(tot);

                order.setPayment(payment);
                orderRepository.save(order);
            }


            PaymentMessage paymentMessage = new PaymentMessage(tot, 0.0, tot);
            Map<String,Integer> itemWiseQuantity = items.entrySet().stream().collect(Collectors.toMap(e ->
                    e.getKey().toString(), e -> e.getValue(), (x,y)->x));
            sendFirebaseCreateOrderMessage(freshCartId, "");
            fireBaseStorageService.sendMessage(String.format("orders/%s/accepted_order", freshCartId),
                    new PlacedOrderMessage(itemWiseQuantity, paymentMessage));
            fireBaseStorageService.sendMessage(String.format("orders/%s/status", freshCartId), OrderStatus.CREATED.toString());
            return new AddPrescriptionResponse(freshCartId);
        } catch (Exception e) {
            throw new ModuleException(e.getMessage());
        }
    }


}
