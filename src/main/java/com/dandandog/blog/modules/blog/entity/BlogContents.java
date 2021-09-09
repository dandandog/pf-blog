package com.dandandog.blog.modules.blog.entity;

import java.time.LocalDateTime;

import com.dandandog.framework.core.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class BlogContents extends AbstractEntity {


    /**
     * 标题
     */
    private String title;

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

    /**
     * 父节点id
     */
    private String parentId;


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
