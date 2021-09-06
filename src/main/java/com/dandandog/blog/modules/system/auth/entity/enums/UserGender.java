package com.dandandog.blog.modules.system.auth.entity.enums;

import com.dandandog.framework.core.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserGender implements BaseEnum<Integer> {

    /**
     * 未知
     */
    UNKNOWN(0, "unknown"),
    /**
     * 男性
     */
    MALE(1, "male"),
    /**
     * 女性
     */
    FEMALE(2, "female");

    private final int value;

    private final String title;

    UserGender(int value, String title) {
        this.value = value;
        this.title = title;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    @JsonValue
    public String getTitle() {
        return this.title;
    }
}
