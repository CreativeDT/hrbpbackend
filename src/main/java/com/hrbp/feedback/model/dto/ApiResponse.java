package com.hrbp.feedback.model.dto;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String errorMessage;

    // Constructors, getters, and setters

    public ApiResponse(boolean success, T data, String errorMessage) {
        this.success = success;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
