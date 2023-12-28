package com.project.swiftShop.dto.user;

public class SignInResponseDto {
    private String status;
    private String message;

    public SignInResponseDto() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SignInResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
