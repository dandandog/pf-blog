package com.dandandog.blog.modules.admin.auth.entity.enums;

import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserStatus implements BaseEnum<Integer> {

    /**
     * 正常
     */
    NORMAL(0, "normal"),
    /**
     * 冻结
     */
    FREEZE(1, "freeze"),
    /**
     * 未激活
     */
    NOT_ACTIVATED(2, "notActivated");

    private final int value;

    private final String title;

    UserStatus(int value, String title) {
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
