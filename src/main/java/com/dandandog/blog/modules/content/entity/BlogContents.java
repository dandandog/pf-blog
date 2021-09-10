package com.dandandog.blog.modules.content.entity;

import java.time.LocalDateTime;

import com.dandandog.framework.core.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.core.entity.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内容(BlogContents)表实体类
 *
 * @author Johnny
 * @since 2021-09-09 14:40:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_contents")
public class BlogContents extends AuditableEntity {


    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 标题
     */
    private String title;

    /**
     * 缩略名
     */
    private String slug;

    /**
     * 文本
     */
    private String text;

    /**
     * 排序
     */
    private Object seq;

    /**
     * 模板
     */
    private String template;

    /**
     * 类型(1:页面;2:附件:3:帖子)
     */
    private Integer type;

    /**
     * 状态(1:发布; 2:未发布)
     */
    private Integer sataus;

    /**
     * 密码
     */
    private String password;

    /**
     * 浏览数量
     */
    private Integer viewNum;

    /**
     * 评论数量
     */
    private Integer commentsNum;

    /**
     * 允许评论
     */
    private Boolean allowComment;

    /**
     * 允许
     */
    private Boolean allowPing;

    /**
     * 允许
     */
    private Boolean allowFeed;


}
