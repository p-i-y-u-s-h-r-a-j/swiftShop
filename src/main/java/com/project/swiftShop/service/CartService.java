package com.project.swiftShop.service;

import com.project.swiftShop.dto.cart.AddToCartDto;
import com.project.swiftShop.dto.cart.CartDto;
import com.project.swiftShop.dto.cart.CartItemDto;
import com.project.swiftShop.exceptions.CustomException;
import com.project.swiftShop.model.Cart;
import com.project.swiftShop.model.Product;
import com.project.swiftShop.model.User;
import com.project.swiftShop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;
    public void addToCart(AddToCartDto addToCartDto, User user) {

        // validate if the product id is valid
        Product product = productService.findById(addToCartDto.getProductId());
        Cart cart = new Cart();

        cart.setProduct(product);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cart.setUser(user);

        // save the cart
        cartRepository.save(cart);
    }

    public CartDto listCartItems(User user) {
        final List<Cart> allCart = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;

        for(Cart cart: allCart){
            CartItemDto cartItemDto = new CartItemDto(cart);
            cartItems.add(cartItemDto);
            totalCost+=cartItemDto.getQuantity()*cart.getProduct().getPrice();
        }
        CartDto cartDto = new CartDto(cartItems,totalCost);
        return cartDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) {
        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);
        if(optionalCart.isEmpty()){
            throw new CustomException("Cart Id is Invalid: "+cartItemId);
        }

        Cart cart = optionalCart.get();
        if(cart.getUser()!=user){
            throw new CustomException("Cart item Does Not Belong to This User");
        }

        cartRepository.delete(cart);
    }
}
