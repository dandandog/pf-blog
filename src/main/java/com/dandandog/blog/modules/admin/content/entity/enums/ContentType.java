package com.dandandog.blog.modules.admin.content.entity.enums;

import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 10:37
 */
public enum ContentType implements BaseEnum<Integer> {

    /**
     * 帖子
     */
    POST(1, "post"),

    /**
     * 附件
     */
    ATTACHMENT(2, "attachment"),

    /**
     * 草案
     */
    DRAFT(3, "draft");

    private final int value;

    private final String title;

    ContentType(int value, String title) {
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
