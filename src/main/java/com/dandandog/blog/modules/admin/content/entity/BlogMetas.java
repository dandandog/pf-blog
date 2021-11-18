package com.dandandog.blog.modules.admin.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.framework.common.model.ITree;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 元数据(BlogMetas)表实体类
 *
 * @author Johnny
 * @since 2021-09-09 14:41:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_metas")
public class BlogMetas extends AuditableEntity implements ITree {

    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 缩写名
     */
    private String slug;

    /**
     * 类型(1:分类;2:标签)
     */
    private MetaType type;

    /**
     * 描述
     */
    private String description;

    /**
     * 数量
     */
    private int count;

    /**
     * 排序
     */
    private int seq;


}
