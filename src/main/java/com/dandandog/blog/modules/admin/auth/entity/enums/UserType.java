package com.dandandog.blog.modules.admin.auth.entity.enums;

import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/23 17:54
 */
public enum UserType implements BaseEnum<Integer> {

    /**
     * admin
     */
    ADMIN(0, "admin"),
    /**
     * user
     */
    USER(1, "user");

    private final int value;

    private final String title;

    UserType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @JsonValue
    public String getTitle() {
        return this.title;
    }

}
