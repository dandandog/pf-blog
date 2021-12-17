package com.dandandog.modules.auth.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResourceTarget implements BaseEnum<Integer> {
    /**
     * 当前页
     */
    CURR_PAGE(0),
    /**
     * 新签页
     */
    NEW_PAGE(1),
    /**
     * 新窗口
     */
    NEW_WINDOW(2);

    private final int value;


    ResourceTarget(int value) {
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
