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
    TEXT(0, false),

    /**
     * 文本域
     */
    TEXT_AREA(1, false),

    /**
     * 多文本
     */
    CHIPS(2, false),

    /**
     * 选择器
     */
    SELECTOR(3, true),

    /**
     * 单选框
     */
    RADIO(4, true),

    /**
     * 多选框
     */
    CHECKBOX(5, true);


    private final int value;

    private final boolean selectable;

    InputType(int value, boolean selectable) {
        this.value = value;
        this.selectable = selectable;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public boolean isSelectable() {
        return this.selectable;
    }

    public String getTitle() {
        return MessageUtil.getMessageEnum(this, LocaleUtil.getCurrLocale());
    }

}
