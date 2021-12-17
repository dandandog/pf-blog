package com.dandandog.modules.auth.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/23 17:54
 */
public enum UserType implements BaseEnum<Integer> {

    /**
     * admin
     */
    ADMIN(0),
    /**
     * user
     */
    USER(1);

    private final int value;


    UserType(int value) {
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
