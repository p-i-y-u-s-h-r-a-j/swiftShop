package com.project.swiftShop.service;

import com.project.swiftShop.dto.ProductDto;
import com.project.swiftShop.model.User;
import com.project.swiftShop.model.Wishlist;
import com.project.swiftShop.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    ProductService productService;
    public void createWishlist(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }

    public List<ProductDto> getWishlistForUser(User user) {
        final List<Wishlist> allWishlist = wishlistRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<ProductDto> productDtos = new ArrayList<>();

        for(Wishlist wishlist: allWishlist){
            productDtos.add(productService.getProductDto(wishlist.getProduct()));
        }
        return productDtos;
    }
}
