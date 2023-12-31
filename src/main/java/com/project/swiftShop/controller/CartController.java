package com.project.swiftShop.controller;

import com.project.swiftShop.commonResponse.ApiResponse;
import com.project.swiftShop.dto.cart.AddToCartDto;
import com.project.swiftShop.dto.cart.CartDto;
import com.project.swiftShop.model.User;
import com.project.swiftShop.service.AuthenticationService;
import com.project.swiftShop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token){

        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        cartService.addToCart(addToCartDto,user);

        return new ResponseEntity<>(new ApiResponse(true,"Added To Cart Successfully!!"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getAllCart(@RequestParam("token") String token){
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        CartDto cartDto = cartService.listCartItems(user);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer cartItemId,
                                                      @RequestParam("token") String token){
        authenticationService.authenticate(token);

        User user = authenticationService.getUser(token);

        cartService.deleteCartItem(cartItemId,user);
        return new ResponseEntity<>(new ApiResponse(true,"Item Removed from Cart"), HttpStatus.OK);
    }

}
