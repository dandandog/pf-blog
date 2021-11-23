package com.dandandog.blog.modules.admin.auth.entity.enums;

import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author JohnnyLiu
 */

public enum ResourceType implements BaseEnum<Integer> {

    /**
     * 导航
     */
    NAV(0, "nav"),
    /**
     * 目录
     */
    CATALOG(1, "catalog"),
    /**
     * 菜单
     */
    MENU(2, "menu"),
    /**
     * 按钮
     */
    BUTTON(3, "button");

    private final int value;

    private final String title;

    ResourceType(int value, String title) {
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
