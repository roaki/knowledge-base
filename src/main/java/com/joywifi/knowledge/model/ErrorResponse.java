package com.joywifi.knowledge.model;

public class ErrorResponse<T> extends AjaxResponse<T> {

    private String errorMessage;

    public ErrorResponse(){
        super(false, null);
    }

    public ErrorResponse(T content) {
        super(false, content);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ErrorResponse setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
