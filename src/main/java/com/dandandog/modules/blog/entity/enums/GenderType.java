package com.dandandog.modules.blog.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum GenderType implements BaseEnum<Integer> {

    /**
     * 男性
     */
    MALE(0),
    /**
     * 女性
     */
    FEMALE(1);

    private final int value;


    GenderType(int value) {
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
