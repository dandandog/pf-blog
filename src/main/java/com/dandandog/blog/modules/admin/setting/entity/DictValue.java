package com.dandandog.blog.modules.admin.setting.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.core.entity.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典值(AppDictValue)表实体类
 *
 * @author makejava
 * @since 2020-11-21 16:40:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dict_value")
public class DictValue extends AuditableEntity {


    /**
     * 字典节点id
     */
    private String nodeId;


    /**
     * 字典标签
     */
    private String label;


    /**
     * 字典值
     */
    private String value;


    /**
     * 排序
     */
    private int seq;


    /**
     * 是否默认值
     */
    private boolean def;


    /**
     * 备注
     */
    private String remark;


}