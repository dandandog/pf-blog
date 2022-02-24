package com.dandandog.modules.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import com.dandandog.modules.blog.entity.enums.InputType;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: JohnnyLiu
 * @Date: 2022/2/24 17:17
 */

@Getter
@Setter
@TableName("config_setting")
public class ConfigSetting extends AuditableEntity {

    /**
     * 排序
     */
    private int seq;

    /**
     * 唯一
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private InputType type;

    /**
     * 是否全局
     */
    private Boolean global;

    /**
     * 选项
     */
    private String options;

}
