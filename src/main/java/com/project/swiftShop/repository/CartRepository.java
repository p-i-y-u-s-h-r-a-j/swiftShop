package com.project.swiftShop.repository;

import com.project.swiftShop.model.Cart;
import com.project.swiftShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
