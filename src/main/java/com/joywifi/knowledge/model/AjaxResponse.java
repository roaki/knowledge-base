package com.joywifi.knowledge.model;

public class AjaxResponse<T> {
    private Boolean success;
    private T content;

    public AjaxResponse(){

    }

    public AjaxResponse(Boolean success, T content) {
        this.success = success;
        this.content = content;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
