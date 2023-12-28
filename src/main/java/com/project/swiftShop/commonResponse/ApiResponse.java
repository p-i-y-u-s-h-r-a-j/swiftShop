package com.project.swiftShop.commonResponse;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public class ApiResponse {
    private boolean isSuccess;
    private String message;

    public ApiResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp(){
        return LocalDateTime.now().toString();
    }
}
