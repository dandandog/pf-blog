package com.dandandog.blog.modules.admin.content.entity.enums;

import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 10:34
 */
public enum MetaType implements BaseEnum<Integer> {

    /**
     * 分类
     */
    CATEGORY(1, "category"),

    /**
     * 标签
     */
    TAG(2, "tag");

    private final int value;

    private final String title;

    MetaType(int value, String title) {
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
