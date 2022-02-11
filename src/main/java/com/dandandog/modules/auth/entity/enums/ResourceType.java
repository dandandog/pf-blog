package com.dandandog.modules.auth.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author JohnnyLiu
 */

public enum ResourceType implements BaseEnum<Integer> {

    /**
     * 菜单
     */
    MENU(0),
    /**
     * 按钮
     */
    BUTTON(1);

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
