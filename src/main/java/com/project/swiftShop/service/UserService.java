package com.project.swiftShop.service;

import com.project.swiftShop.dto.ResponseDto;
import com.project.swiftShop.dto.user.SignInDto;
import com.project.swiftShop.dto.user.SignInResponseDto;
import com.project.swiftShop.dto.user.SignUpDto;
import com.project.swiftShop.exceptions.AuthenticationFailedException;
import com.project.swiftShop.exceptions.CustomException;
import com.project.swiftShop.model.AuthenticationToken;
import com.project.swiftShop.model.User;
import com.project.swiftShop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;



@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Transactional
    public ResponseDto signUp(SignUpDto signUpDto) {

        /*-------------Check If User Already Exist------------*/
        if(Objects.nonNull(userRepository.findByEmail(signUpDto.getEmail()))){
            throw new CustomException("User Already Exist");
        }
        String encryptedPassword = signUpDto.getPassword();
        /*-------------Hash The Password------------*/
        try{
            encryptedPassword = hashPassword(signUpDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(e.getMessage());
        }
        /*-------------Save The User------------*/
        User user = new User(signUpDto.getFirstName(), signUpDto.getLastName(), signUpDto.getEmail(), encryptedPassword);
        userRepository.save(user);
        /*-------------Create Token------------*/

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);


        ResponseDto responseDto = new ResponseDto("Success", "User Created Successfully");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte[] digest = md.digest();
        String hash = Base64.getEncoder().encodeToString(digest);
        return hash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        User user = userRepository.findByEmail(signInDto.getEmail());
        if(Objects.isNull(user)){
            throw new AuthenticationFailedException("User is Not Valid");
        }

        try{
            if(!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                throw new AuthenticationFailedException("Wrong Password!!");
            }
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if(Objects.isNull(token)){
            throw new CustomException("Token Not Found");
        }

        return new SignInResponseDto("Success", token.getToken());
    }
}
