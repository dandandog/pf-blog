package com.dandandog.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import com.dandandog.modules.sys.entity.enums.InputType;
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
     * 字典值标识
     */
    private String code;


    /**
     * 排序
     */
    private int seq;


    /**
     * 是否必填
     */
    private boolean req;


    /**
     * 值输入类型
     */
    private InputType type;


    /**
     * 默认值
     */
    private Object value;


    /**
     * 备注
     */
    private String remark;

}