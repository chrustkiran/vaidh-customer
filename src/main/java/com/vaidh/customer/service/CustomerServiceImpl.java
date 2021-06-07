package com.vaidh.customer.service;

import com.vaidh.customer.dto.ItemAddedResponse;
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

@Service
public class CustomerServiceImpl implements CustomerService {
    private final String FRESH_CART_REF_ID_JOINER = "_";
    @Autowired
    ProductRepository productRepository;

    @Autowired
    FreshCartRepository freshCartRepository;

    @Autowired
    FreshCartItemRepository freshCartItemRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ItemAddedResponse addItemToCart(Long productId, Integer quantity) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<FreshCart> freshCart = freshCartRepository.findByUsername(username);
            //check there is any shopping cart for this user
            if (!freshCart.isPresent()) {
                String freshCartReferenceId = username + FRESH_CART_REF_ID_JOINER + System.currentTimeMillis();
                freshCartRepository.save(new FreshCart(username, FreshCartStatus.ACTIVE, freshCartReferenceId));
            } else {
                if (freshCart.get().getStatus().equals(FreshCartStatus.ACTIVE.toString())) {
                    freshCartItemRepository.save(new FreshCartItem(freshCart.get().getCartReferenceId(), productId, quantity));
                } else {
                    String freshCartRefId = username + FRESH_CART_REF_ID_JOINER + System.currentTimeMillis();
                    freshCartRepository.save(new FreshCart(freshCart.get().getFreshCartId(), FreshCartStatus.ACTIVE,
                            freshCartRefId));
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public ItemAddedResponse addPrescriptionToCart(MultipartFile file) {
        return null;
    }


}
