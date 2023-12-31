package com.project.swiftShop.controller;


import com.project.swiftShop.dto.ResponseDto;
import com.project.swiftShop.dto.user.SignInDto;
import com.project.swiftShop.dto.user.SignInResponseDto;
import com.project.swiftShop.dto.user.SignUpDto;
import com.project.swiftShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseDto signUp(@RequestBody SignUpDto signUpDto){
        return userService.signUp(signUpDto);
    }

    @PostMapping("/signIn")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto){
        return userService.signIn(signInDto);
    }
}
