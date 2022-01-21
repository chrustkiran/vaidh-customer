package com.vaidh.customer.service;

import com.vaidh.customer.constants.ResponseMessage;
import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.dto.ProductDTO;
import com.vaidh.customer.dto.request.ModifyOrderRequest;
import com.vaidh.customer.dto.request.ModifyProductRequest;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.message.CancelledOrderMessage;
import com.vaidh.customer.message.PaymentMessage;
import com.vaidh.customer.message.PlacedOrderMessage;
import com.vaidh.customer.model.enums.ModifiedType;
import com.vaidh.customer.model.enums.OrderStatus;
import com.vaidh.customer.model.enums.PaymentMethod;
import com.vaidh.customer.model.enums.ProductStatus;
import com.vaidh.customer.model.inventory.*;
import com.vaidh.customer.repository.FreshCartItemRepository;
import com.vaidh.customer.repository.ModifiedCartItemRepository;
import com.vaidh.customer.repository.OrderRepository;
import com.vaidh.customer.repository.ProductRepository;
import com.vaidh.customer.util.InventoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.vaidh.customer.constants.ResponseMessage.SUCCESSFULLY_MODIFIED;
import static com.vaidh.customer.constants.ResponseMessage.SUCCESSFULLY_PLACED_ORDER;

@Service
public class InventoryServiceImpl implements InventoryService{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModifiedCartItemRepository modifiedCartItemRepository;

    @Autowired
    FreshCartItemRepository freshCartItemRepository;

    @Autowired
    PaymentService paymentService;

    @Autowired
    FireBaseStorageServiceImpl fireBaseStorageService;

    @Override
    public boolean addProduct(ProductDTO productDTO) throws ModuleException {
        if (productDTO != null) {
            try {
                Product product = InventoryUtil.convertToProduct(productDTO);
                product.setProductStatus(ProductStatus.ACTIVE);
                productRepository.save(product);
                return true;
            } catch (Exception e) {
                throw new ModuleException("Product Adding Failed");
            }
        }
        return false;
    }

    @Override
    public boolean deActivateProduct(String productId) throws ModuleException {
        if (!productId.isEmpty()) {
            try {
                Product product = productRepository.findById(Long.parseLong(productId)).get();
                product.setProductStatus(ProductStatus.DE_ACTIVE);
                productRepository.save(product);
                return true;
            } catch (Exception e) {
                throw new ModuleException(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public CommonMessageResponse modifyOrder(ModifyOrderRequest modifyOrderRequest) {
        List<Product> products = productRepository.findAllById(modifyOrderRequest.getProducts().stream().map(prod
                -> prod.getProductId()).collect(Collectors.toList()));
        Map<Long, Double> productPriceByProductId = products.stream().collect(Collectors.toMap(Product::getProductId,
                Product::getPrice, (k1, k2)-> k1));
        List<ModifiedCartItem> modifiedCartItems = InventoryUtil.getModifiedCardItems(modifyOrderRequest, productPriceByProductId);
        modifiedCartItemRepository.saveAll(modifiedCartItems);

        Optional<Order> order = orderRepository.findByFreshCartReferenceId(modifyOrderRequest.getReferenceCartId());
        if (order.isPresent()) {
            Order ordert = order.get();
            ordert.setOrderStatus(OrderStatus.VERIFIED);
            ordert.setPayment(getPaymentForModifiedCartItems(modifiedCartItems));
            ordert.setOrderModifiedTime(modifyOrderRequest.getModifiedTime());
            orderRepository.save(ordert);
        }

        return new CommonMessageResponse(SUCCESSFULLY_PLACED_ORDER);
    }

    private Payment getPaymentForModifiedCartItems(List<ModifiedCartItem> modifiedCartItems) {
        if (modifiedCartItems != null || modifiedCartItems.isEmpty()) return null;
        Payment payment = new Payment();
        Double totalAmount = modifiedCartItems.stream().filter(modifiedCartItem -> modifiedCartItem.getModifiedType() != ModifiedType.REMOVED)
                .collect(Collectors.summingDouble(modifiedCartItem -> modifiedCartItem.getCurrentPrice()));
        payment.setModifiedAmount(totalAmount);
        payment.setNetAmount(totalAmount);
        payment.setOfferAmount(0.0);
        payment.setTotalAmount(totalAmount);
        return payment;
    }

    @Override
    public CommonMessageResponse modifyProduct(ModifyProductRequest modifyProductRequest) throws ModuleException {
        if (modifyProductRequest != null && modifyProductRequest.getProductId() != null) {
            Product savedProduct = productRepository.findById(modifyProductRequest.getProductId()).get();
            if (savedProduct != null) {
                if (!modifyProductRequest.getImageUrl().isEmpty()) {
                    savedProduct.setImageUrl(modifyProductRequest.getImageUrl());
                }
                if (!modifyProductRequest.getName().isEmpty()) {
                    savedProduct.setName(modifyProductRequest.getName());
                }
                if (modifyProductRequest.getPrice() != null) {
                    savedProduct.setPrice(modifyProductRequest.getPrice());
                }
                if (modifyProductRequest.getProductCategory() != null) {
                    savedProduct.setProductCategory(modifyProductRequest.getProductCategory());
                }
                if (modifyProductRequest.getProductStatus() != null) {
                    savedProduct.setProductStatus(modifyProductRequest.getProductStatus());
                }
                if (modifyProductRequest.getProductUnit() != null) {
                    savedProduct.setProductUnit(modifyProductRequest.getProductUnit());
                }

                productRepository.save(savedProduct);
                return new CommonMessageResponse(SUCCESSFULLY_MODIFIED);
            } else {
                throw new ModuleException("No Product with id : " + modifyProductRequest.getProductId());
            }
        } else {
            throw new ModuleException("Some fields are missing");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public boolean addProducts(List<ProductDTO> products) throws ModuleException {
        if (products != null & !products.isEmpty()) {
            try {
                List<Product> productEntities = products.stream().map(product -> {
                            Product proEntity = InventoryUtil.convertToProduct(product);
                            proEntity.setProductStatus(ProductStatus.ACTIVE);
                            return proEntity;
                        }
                ).collect(Collectors.toList());
                productRepository.saveAll(productEntities);
                return true;
            } catch (Exception ex) {
                throw new ModuleException("Product Adding Failed");
            }
        }
        return false;
    }
    @Override
    public CommonMessageResponse addItemToCart(Long productId, Integer quantity, String freshCartId) throws ModuleException {
        try {
            Optional<Product> product = productRepository.findById(productId);
            if (product.isPresent()) {
                freshCartItemRepository.save(new FreshCartItem(freshCartId, productId, quantity, product.get().getPrice()));
                return new CommonMessageResponse(ResponseMessage.SUCCESSFULLY_ADDED);
            } else {
                throw new ModuleException("No product exists with id " + productId);
            }
        } catch (Exception e) {
            throw new ModuleException(e.getMessage());
        }
    }

    @Override
    public CommonMessageResponse addItemToCartAndPlaceOrder(Map<Long, Integer> items, String freshCartId) throws ModuleException {
        try {
            List<Product> products = productRepository.findAllById(items.keySet());
            Map<Long, Double> productKeyWisePrice = products.stream().collect(Collectors.toMap(Product::getProductId,
                    Product::getPrice, (x,y)->x));
            List<FreshCartItem> freshCartItems = Optional.ofNullable(items.entrySet()).orElse(new HashSet<>()).
                    stream().map(item -> new FreshCartItem(freshCartId, item.getKey(), item.getValue(),
                    productKeyWisePrice.get(item.getKey())))
                    .collect(Collectors.toList());
            freshCartItemRepository.saveAll(freshCartItems);

            Optional<Order> order = orderRepository.findByFreshCartReferenceId(freshCartId);
            Double tot = 0.0;
            if (order.isPresent()) {
                Order orderEnt = order.get();
                orderEnt.setOrderStatus(OrderStatus.ACCEPTED);

                tot = paymentService.calculateTotalPaymentOfOrder(freshCartItems);
                Payment payment = new Payment();
                payment.setTotalAmount(tot);
                payment.setNetAmount(tot);

                orderEnt.setPayment(payment);
                orderRepository.save(orderEnt);
            }


            PaymentMessage paymentMessage = new PaymentMessage(tot, 0.0, tot);
            Map<String,Integer> itemWiseQuantity = items.entrySet().stream().collect(Collectors.toMap(e ->
                    e.getKey().toString(), e -> e.getValue(), (x,y)->x));
            fireBaseStorageService.sendMessage(String.format("orders/%s/accepted_order", freshCartId),
                    new PlacedOrderMessage(itemWiseQuantity, paymentMessage));
            fireBaseStorageService.sendMessage(String.format("orders/%s/status", freshCartId), OrderStatus.ACCEPTED.toString());
            return new CommonMessageResponse(ResponseMessage.SUCCESSFULLY_ADDED);
        } catch (Exception e) {
            throw new ModuleException(e.getMessage());
        }
    }


    @Override
    public CommonMessageResponse cancelOrder(String freshCartId, String note) throws ModuleException {
        if (freshCartId != null && !freshCartId.isEmpty()) {
            Optional<Order> order = orderRepository.findByFreshCartReferenceId(freshCartId);
            if (order.isPresent()) {
                Order orderEnt = order.get();
                orderEnt.setOrderStatus(OrderStatus.CANCELED);
                orderEnt.setFailureNote(note);
                orderRepository.save(orderEnt);
                fireBaseStorageService.sendMessage(String.format("orders/%s/cancelled_order", freshCartId), new CancelledOrderMessage(note));
                fireBaseStorageService.sendMessage(String.format("orders/%s/status", freshCartId), OrderStatus.CANCELED.toString());
            }

        } else {
            throw new ModuleException("invalid reference id");
        }

        return new CommonMessageResponse("Order cancelled :: ref id :: " + freshCartId);
    }

    @Override
    public CommonMessageResponse acceptOrder(String referenceId) throws ModuleException {
        if (referenceId != null && !referenceId.isEmpty()) {
            Optional<Order> order = orderRepository.findByFreshCartReferenceId(referenceId);
            if (order.isPresent()) {
                Order orderEnt = order.get();
                orderEnt.setOrderStatus(OrderStatus.ACCEPTED);
                orderRepository.save(orderEnt);
                fireBaseStorageService.sendMessage(String.format("orders/%s/status", referenceId), OrderStatus.ACCEPTED.toString());
            }
        } else {
            throw new ModuleException("invalid reference id");
        }

        return new CommonMessageResponse("Order accpeted :: ref id :: " + referenceId);
    }

    @Override
    public CommonMessageResponse editProducts(List<ProductDTO> products) throws Exception {
        if (products != null && !products.isEmpty()) {
            Map<Long, ProductDTO> productDTOMap = products.stream().collect(Collectors.toMap(ProductDTO::getId, x->x, (x,y)->x));
            List<Product> productObs = productRepository.findAllById(productDTOMap.keySet());

            for (Product product : productObs) {
                ProductDTO productdto = productDTOMap.get(product.getProductId());

                if (productdto.getCompanyName() != null && !productdto.getCompanyName().isEmpty()) {
                    product.setCompanyName(productdto.getCompanyName());
                }

                if (productdto.getName() != null && !productdto.getName().isEmpty()) {
                    product.setName(productdto.getName());
                }

                if (productdto.getPrice() != null) {
                    product.setPrice(productdto.getPrice());
                }

                if (productdto.getProductCategory() != null) {
                    product.setProductCategory(productdto.getProductCategory());
                }

                if (productdto.getDescription() != null && !productdto.getDescription().isEmpty()) {
                    product.setDescription(productdto.getDescription());
                }

                if (productdto.getImageURL() != null && !productdto.getImageURL().isEmpty()) {
                    product.setImageUrl(productdto.getImageURL());
                }

            }

            productRepository.saveAll(productObs);
        }
        return new CommonMessageResponse("success");
    }

}
