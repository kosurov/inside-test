package com.github.kosurov.insidetest.web.response;

public class BaseWebResponse {
    private String errorMessage;

    public BaseWebResponse() {
    }

    public BaseWebResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
