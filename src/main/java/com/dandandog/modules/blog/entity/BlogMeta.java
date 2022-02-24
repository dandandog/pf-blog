package com.dandandog.modules.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.modules.blog.entity.enums.MetaType;
import com.dandandog.framework.common.model.ITree;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 元数据(BlogMetas)表实体类
 *
 * @author Johnny
 * @since 2021-09-09 14:41:00
 */
@Getter
@Setter
@TableName("blog_meta")
public class BlogMeta extends AuditableEntity implements ITree {

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
     * 层级
     */
    private String level;


}
