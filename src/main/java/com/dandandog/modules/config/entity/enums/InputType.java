package com.dandandog.modules.config.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2022/2/24 17:33
 */
public enum InputType implements BaseEnum<Integer> {

    /**
     * 文本
     */
    TEXT(0),

    TEXT_AREA(1),

    CHIPS(2),

    SELECTOR(3),

    RADIO(4),

    CHECKBOX(5);

    private final int value;


    InputType(int value) {
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
