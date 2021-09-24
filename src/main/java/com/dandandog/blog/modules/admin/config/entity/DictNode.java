package com.dandandog.blog.modules.admin.config.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.common.model.ITree;
import com.dandandog.framework.core.entity.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典节点(AppDictNode)表实体类
 *
 * @author makejava
 * @since 2020-11-21 16:40:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("dict_node")
public class DictNode extends AuditableEntity implements ITree {


    /**
     * 父id
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String parentId;


    /**
     * 节点名称
     */
    private String name;


    /**
     * 唯一标识
     */
    private String code;


    /**
     * 排序
     */
    private int seq = 1;


    /**
     * 是否叶子节点
     */
    private boolean leaf = true;


    /**
     * 备注
     */
    private String remark;


}