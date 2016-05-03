package com.joywifi.knowledge.model;

public class SuccessResponse<T> extends AjaxResponse<T> {

    public SuccessResponse(T content) {
        super(true, content);
    }
}
