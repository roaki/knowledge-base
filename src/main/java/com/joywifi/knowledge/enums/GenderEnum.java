package com.joywifi.knowledge.enums;

/**
 * <p>性别</p>
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-14 下午2:12
 * <p>Version: 1.0
 */
public enum GenderEnum {

    male("男"), female("女");
    private final String info;

    GenderEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
