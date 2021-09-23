package com.dandandog.blog.modules.admin.setting.entity.enums;

import com.dandandog.framework.core.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/23 16:57
 */
public enum InputType implements BaseEnum<Integer> {

    /**
     * 文本
     */
    TEXT(0, "text"),
    /**
     * 文本域
     */
    TEXT_AREA(1, "textarea"),

    /**
     * 选择器
     */
    SELECTOR(2, "selector"),

    /**
     * 单选框
     */
    RADIO(3, "radio"),

    /**
     * 多选框
     */
    CHECKBOX(4, "checkbox");


    private final int value;

    private final String title;

    InputType(int value, String title) {
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
