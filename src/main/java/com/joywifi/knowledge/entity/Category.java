package com.joywifi.knowledge.entity;

import org.hibernate.validator.constraints.NotBlank;

import com.joywifi.knowledge.annotation.Unique;

public class Category extends BaseEntity {
    @Unique
    @NotBlank
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
