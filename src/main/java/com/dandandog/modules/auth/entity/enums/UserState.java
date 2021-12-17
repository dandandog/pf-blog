package com.dandandog.modules.auth.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserState implements BaseEnum<Integer> {

    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 冻结
     */
    FREEZE(1),
    /**
     * 未激活
     */
    NOT_ACTIVATED(2);

    private final int value;


    UserState(int value) {
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
