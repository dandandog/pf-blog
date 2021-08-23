package com.dandandog.blog.modules.system.entity.enums;

import com.dandandog.framework.core.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/23 17:54
 */
public enum UserType implements BaseEnum<Integer> {

    /**
     * 正常
     */
    ADMIN(0, "normal"),
    /**
     * 冻结
     */
    USER(1, "freeze");

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

    @Override
    @JsonValue
    public String getTitle() {
        return this.title;
    }

}
