package com.dandandog.modules.blog.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 10:37
 */
public enum ContentType implements BaseEnum<Integer> {

    /**
     * 帖子
     */
    POST(0),

    /**
     * 附件
     */
    ATTACHMENT(1),

    /**
     * 草案
     */
    DRAFT(2);

    private final int value;


    ContentType(int value) {
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
