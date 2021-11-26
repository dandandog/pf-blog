package com.dandandog.blog.modules.admin.auth.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserGender implements BaseEnum<Integer> {

    /**
     * 未知
     */
    UNKNOWN(0),
    /**
     * 男性
     */
    MALE(1),
    /**
     * 女性
     */
    FEMALE(2);

    private final int value;


    UserGender(int value) {
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
