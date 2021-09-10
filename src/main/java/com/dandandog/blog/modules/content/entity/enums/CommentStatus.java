package com.dandandog.blog.modules.content.entity.enums;

import com.dandandog.framework.core.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 10:49
 */
public enum CommentStatus implements BaseEnum<Integer> {

    /**
     * 通过
     */
    PASSED(1, "passed"),

    /**
     * 待审核
     */
    PENDING(2, "pending"),

    /**
     * 屏蔽
     */
    SHIELDING(3, "shielding");


    private final int value;

    private final String title;

    CommentStatus(int value, String title) {
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
