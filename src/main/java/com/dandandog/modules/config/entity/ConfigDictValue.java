package com.dandandog.modules.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典值(AppDictValue)表实体类
 *
 * @author makejava
 * @since 2020-11-21 16:40:42
 */
@Getter
@Setter
@TableName("config_dict_value")
public class ConfigDictValue extends AuditableEntity {


    /**
     * 字典节点id
     */
    private String nodeId;


    /**
     * 字典标签
     */
    private String label;


    /**
     * 值
     */
    private String value;


    /**
     * 排序
     */
    private int seq;


    /**
     * 是否必填
     */
    private boolean enable;


    /**
     * 备注
     */
    private String remark;

}