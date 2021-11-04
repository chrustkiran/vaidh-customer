package com.vaidh.customer.service;

import com.vaidh.customer.constants.ResponseMessage;
import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.dto.ItemAddedResponse;
import com.vaidh.customer.dto.request.ModifyUserRequest;
import com.vaidh.customer.dto.response.CommonMessageResponse;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.message.OrderCreateMessage;
import com.vaidh.customer.model.customer.UserEntity;
import com.vaidh.customer.model.enums.FreshCartStatus;
import com.vaidh.customer.model.inventory.FreshCart;
import com.vaidh.customer.model.inventory.FreshCartItem;
import com.vaidh.customer.model.inventory.Order;
import com.vaidh.customer.model.inventory.Product;
import com.vaidh.customer.repository.*;
import com.vaidh.customer.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import static com.vaidh.customer.constants.ResponseMessage.SUCCESSFULLY_MODIFIED;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final String FRESH_CART_REF_ID_JOINER = "_";
    @Autowired
    ProductRepository productRepository;

    @Autowired
    FreshCartRepository freshCartRepository;

    @Autowired
    FreshCartItemRepository freshCartItemRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    FireBaseStorageServiceImpl fireBaseStorageService;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAllActiveProducts();
    }

    @Override
    public CommonMessageResponse addItemToCart(Long productId, Integer quantity) throws ModuleException {
        try {
            freshCartItemRepository.save(new FreshCartItem(getFreshCartId(), productId, quantity));
            return new CommonMessageResponse(ResponseMessage.SUCCESSFULLY_ADDED);
        } catch (Exception e) {
            throw new ModuleException(e.getMessage());
        }
    }

    @Override
    public CommonMessageResponse addPrescriptionToCart(MultipartFile file) throws ModuleException {
        try {
            String url = fileStorageService.save(file);
            freshCartItemRepository.save(new FreshCartItem(getFreshCartId(), url));
            return new CommonMessageResponse("Successfully added");
        } catch (Exception e) {
            throw new ModuleException(e.getMessage());
        }
    }

    @Override
    public CommonMessageResponse placeOrder() {
        FreshCart currentFreshCart = freshCartRepository.findByUsername(authenticationService.getCurrentUserName()).get();
        //save new order entry to db
        orderRepository.save(new Order(currentFreshCart.getCartReferenceId(), authenticationService.getCurrentUserName(),
                new Date()));
        //active cart -> deactive
        deActivateCurrentFreshCart(currentFreshCart);
        //fetching cart item details
        List<FreshCartItem> freshCartItems = freshCartItemRepository.findByFreshCartReferenceId
                (currentFreshCart.getCartReferenceId());
        //sending messaging to firebase
        fireBaseStorageService.saveTestDate(currentFreshCart.getCartReferenceId(), new OrderCreateMessage(
                authenticationService.getCurrentUserName(), freshCartItems.stream().filter(freshCartItem ->
                freshCartItem.getProductId() != null).collect(Collectors.toMap(FreshCartItem::getProductId,
                FreshCartItem::getQuantity, (x,y)-> x))
        , freshCartItems.stream().filter(freshCartItem -> !freshCartItem.getPrescribedImage().isEmpty()).map(freshCartItem ->
                freshCartItem.getPrescribedImage()).collect(Collectors.toList())));
        return new CommonMessageResponse(ResponseMessage.SUCCESSFULLY_PLACED_ORDER + " :: " + currentFreshCart.getCartReferenceId());
    }

    @Override
    public List<CommonResults> getHistories() {
        return null;
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
        return productRepository.findProductsByName(name);
    }

    private void deActivateCurrentFreshCart(FreshCart freshCart) {
        freshCart.setStatus(FreshCartStatus.DE_ACTIVE);
        freshCartRepository.save(freshCart);
    }


    private String getFreshCartId() {
        String username = authenticationService.getCurrentUserName();
        Optional<FreshCart> freshCart = freshCartRepository.findByUsername(username);

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
                freshCartRepository.save(new FreshCart(freshCart.get().getFreshCartId(), FreshCartStatus.ACTIVE,
                        freshCartRefId));
                return freshCartRefId;
            }
        }
    }



}
