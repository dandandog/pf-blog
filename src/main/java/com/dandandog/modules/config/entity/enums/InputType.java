package com.dandandog.modules.config.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/23 16:57
 */
public enum InputType implements BaseEnum<Integer> {

    /**
     * 文本
     */
    TEXT(0),

    /**
     * 文本域
     */
    TEXT_AREA(1),

    /**
     * 选择器
     */
    SELECTOR(2),

    /**
     * 单选框
     */
    RADIO(3),

    /**
     * 多选框
     */
    CHECKBOX(4),

    /**
     * 多文本
     */
    CHIPS(5);


    private final int value;

    InputType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getTitle() {
        return MessageUtil.getMessageEnum(this, LocaleUtil.getCurrLocale());
    }

}
