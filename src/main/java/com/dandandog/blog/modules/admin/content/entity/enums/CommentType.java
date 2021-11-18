package com.dandandog.blog.modules.admin.content.entity.enums;

import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 10:46
 */
public enum CommentType implements BaseEnum<Integer> {

    /**
     * 评论
     */
    COMMENT(1, "comment"),

    /**
     * 回复
     */
    REPLY(2, "reply");

    private final int value;

    private final String title;

    CommentType(int value, String title) {
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
