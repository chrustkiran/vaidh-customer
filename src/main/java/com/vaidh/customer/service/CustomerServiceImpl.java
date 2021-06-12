package com.vaidh.customer.service;

import com.vaidh.customer.constants.ResponseMessage;
import com.vaidh.customer.dto.ItemAddedResponse;
import com.vaidh.customer.dto.OrderPlacedResponse;
import com.vaidh.customer.exception.ModuleException;
import com.vaidh.customer.model.enums.FreshCartStatus;
import com.vaidh.customer.model.inventory.FreshCart;
import com.vaidh.customer.model.inventory.FreshCartItem;
import com.vaidh.customer.model.inventory.Product;
import com.vaidh.customer.repository.FreshCartItemRepository;
import com.vaidh.customer.repository.FreshCartRepository;
import com.vaidh.customer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ItemAddedResponse addItemToCart(Long productId, Integer quantity) throws ModuleException {
        try {
            freshCartItemRepository.save(new FreshCartItem(getFreshCartId(), productId, quantity));
            return new ItemAddedResponse(ResponseMessage.SUCCESSFULLY_ADDED);
        } catch (Exception e) {
            throw new ModuleException(e.getMessage());
        }
    }

    @Override
    public ItemAddedResponse addPrescriptionToCart(MultipartFile file) throws ModuleException {
        try {
            String url = fileStorageService.save(file);
            freshCartItemRepository.save(new FreshCartItem(getFreshCartId(), url));
            return new ItemAddedResponse("Successfully added");
        } catch (Exception e) {
            throw new ModuleException(e.getMessage());
        }
    }

    @Override
    public OrderPlacedResponse placeOrder() {
        UUID uuid = UUID.randomUUID();
        uuid.toString();
        return null;
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
