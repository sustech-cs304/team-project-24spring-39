package com.example.cs304.response;

public class MetaResponse {

    private StatusCode status;
    private String message;

    public MetaResponse(StatusCode status, String message) {
        this.status = status;
        this.message = message;
    }

    // getters and setters
    public StatusCode getStatus() {
        return status;
    }

}