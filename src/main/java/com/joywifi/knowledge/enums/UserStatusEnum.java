package com.joywifi.knowledge.enums;

public enum UserStatusEnum {

    enable("enable", "正常"), disable("disable", "禁用");
    private final String status;
    private final String info;

    UserStatusEnum(String status, String info) {
        this.status = status;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public String getStatus() {
        return status;
    }
}