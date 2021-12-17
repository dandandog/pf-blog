package com.dandandog.modules.blog.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 10:34
 */
public enum MetaType implements BaseEnum<Integer> {

    /**
     * 分类
     */
    CATEGORY(0),

    /**
     * 标签
     */
    TAG(1);

    private final int value;


    MetaType(int value) {
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
