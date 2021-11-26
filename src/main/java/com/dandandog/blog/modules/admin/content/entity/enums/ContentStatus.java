package com.dandandog.blog.modules.admin.content.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 10:25
 */
public enum ContentStatus implements BaseEnum<Integer> {

    /**
     * 发布:全部可见
     */
    PUBLISH(0),

    /**
     * 密码:密码可见
     */
    PASSWORD(1),

    /**
     * 私密:自己可见
     */
    PRIVATE(2),

    /**
     * 隐藏:不可见
     */
    HIDDEN(4),

    /**
     * 待审核
     */
    PENDING(5);


    private final int value;


    ContentStatus(int value) {
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
