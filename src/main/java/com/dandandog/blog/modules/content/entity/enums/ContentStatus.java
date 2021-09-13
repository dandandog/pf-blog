package com.dandandog.blog.modules.content.entity.enums;

import com.dandandog.framework.core.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 10:25
 */
public enum ContentStatus implements BaseEnum<Integer> {

    /**
     * 发布:全部可见
     */
    PUBLISH(1, "publish"),

    /**
     * 密码:密码可见
     */
    PASSWORD(2, "password"),

    /**
     * 私密:自己可见
     */
    PRIVATE(3, "private"),

    /**
     * 隐藏:不可见
     */
    HIDDEN(4, "hidden"),

    /**
     * 待审核
     */
    PENDING(5, "pending");


    private final int value;

    private final String title;

    ContentStatus(int value, String title) {
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
