package com.dandandog.blog.modules.admin.auth.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author JohnnyLiu
 */

public enum ResourceType implements BaseEnum<Integer> {

    /**
     * 导航
     */
    NAV(0),
    /**
     * 目录
     */
    CATALOG(1),
    /**
     * 菜单
     */
    MENU(2),
    /**
     * 按钮
     */
    BUTTON(3);

    private final int value;

    ResourceType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @JsonValue
    public String getTitle() {
        return MessageUtil.getMessageEnum(this, LocaleUtil.getCurrLocale());
    }
}
