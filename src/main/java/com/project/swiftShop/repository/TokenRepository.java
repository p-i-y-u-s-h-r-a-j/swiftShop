package com.project.swiftShop.repository;

import com.project.swiftShop.model.AuthenticationToken;
import com.project.swiftShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findByUser(User user);
    AuthenticationToken findByToken(String token);
}
