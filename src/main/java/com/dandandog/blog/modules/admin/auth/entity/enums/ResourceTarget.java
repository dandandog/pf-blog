package com.dandandog.blog.modules.admin.auth.entity.enums;

import com.dandandog.framework.core.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResourceTarget implements BaseEnum<Integer> {
    /**
     * 当前页
     */
    CURR_PAGE(0, "currPage"),
    /**
     * 新签页
     */
    NEW_PAGE(1, "newPage"),

    /**
     * 新窗口
     */
    NEW_WINDOW(2, "newWindow");

    private final int value;

    private final String title;

    ResourceTarget(int value, String title) {
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
