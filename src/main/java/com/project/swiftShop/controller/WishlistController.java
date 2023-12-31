package com.project.swiftShop.controller;

import com.project.swiftShop.commonResponse.ApiResponse;
import com.project.swiftShop.dto.ProductDto;
import com.project.swiftShop.model.Product;
import com.project.swiftShop.model.User;
import com.project.swiftShop.model.Wishlist;
import com.project.swiftShop.service.AuthenticationService;
import com.project.swiftShop.service.WishlistService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wishlist")
@CrossOrigin(origins = "http://localhost:3000")
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishlist(@RequestBody Product product, @RequestParam("token") String token){
         authenticationService.authenticate(token);
         /*----Task --> Check if product is already wishlisted by user-----*/
         User user = authenticationService.getUser(token);

         Wishlist wishlist = new Wishlist(user, product);

         wishlistService.createWishlist(wishlist);
         return new ResponseEntity<>(new ApiResponse(true, "Wishlist Created Successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishlist(@PathVariable("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        List<ProductDto> productDtos = wishlistService.getWishlistForUser(user);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}
