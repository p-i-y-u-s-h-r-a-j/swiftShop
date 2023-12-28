package com.project.swiftShop.exceptions;

public class AuthenticationFailedException extends IllegalArgumentException{
    public AuthenticationFailedException(String msg){
        super(msg);
    }
}
