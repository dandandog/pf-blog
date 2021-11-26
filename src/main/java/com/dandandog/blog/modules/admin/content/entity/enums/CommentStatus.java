package com.dandandog.blog.modules.admin.content.entity.enums;

import com.dandandog.framework.common.utils.LocaleUtil;
import com.dandandog.framework.common.utils.MessageUtil;
import com.dandandog.framework.mybatis.entity.enums.BaseEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 10:49
 */
public enum CommentStatus implements BaseEnum<Integer> {

    /**
     * 通过
     */
    PASSED(0),

    /**
     * 待审核
     */
    PENDING(1),

    /**
     * 屏蔽
     */
    SHIELDING(2);


    private final int value;


    CommentStatus(int value) {
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
