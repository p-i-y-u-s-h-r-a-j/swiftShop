package com.project.swiftShop.dto.checkOut;

public class StripeResponse {

    private String sessionId;
    public StripeResponse(String id) {
        this.sessionId = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
