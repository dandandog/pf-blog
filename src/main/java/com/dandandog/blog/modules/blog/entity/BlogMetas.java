package com.dandandog.blog.modules.blog.entity;

import java.time.LocalDateTime;

import com.dandandog.framework.core.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class BlogMetas extends AbstractEntity {


    /**
     * 名称
     */
    private String name;

    /**
     * 唯一标识
     */
    private String slug;

    /**
     * 类型(1:分类;2:标签)
     */
    private Integer type;

    /**
     * 描述
     */
    private String description;

    /**
     * 数量
     */
    private Object count;

    /**
     * 排序
     */
    private Object seq;

    /**
     * 父节点id
     */
    private String parntId;


    /**
     * 创建者
     */
    private String creator;

    /**
     * 更新者
     */
    private String operator;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime operatedTime;
}
