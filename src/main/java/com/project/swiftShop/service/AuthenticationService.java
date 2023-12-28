package com.project.swiftShop.service;

import com.project.swiftShop.exceptions.AuthenticationFailedException;
import com.project.swiftShop.model.AuthenticationToken;
import com.project.swiftShop.model.User;
import com.project.swiftShop.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {
    @Autowired
    TokenRepository tokenRepository;
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public User getUser(String token){
        final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);

        if(Objects.isNull(authenticationToken)){
            return null;
        }
        return authenticationToken.getUser();
    }
    public void authenticate(String token) throws AuthenticationFailedException{
        if(Objects.isNull(token)){
            throw new AuthenticationFailedException("Token Not Exist");
        }
        if(Objects.isNull(getUser(token))){
            throw new AuthenticationFailedException("Token Not Valid");
        }
    }
}
