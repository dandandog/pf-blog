package com.dandandog.blog.modules.admin.content.entity;

import com.dandandog.blog.modules.admin.content.entity.enums.CommentStatus;
import com.dandandog.blog.modules.admin.content.entity.enums.CommentType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.core.entity.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 评论(BlogComments)表实体类
 *
 * @author Johnny
 * @since 2021-09-09 14:40:53
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_comments")
public class BlogComments extends AuditableEntity {


    /**
     * 父节点id
     */
    private String parentId;

    /**
     * 内容id
     */
    private String contentId;

    /**
     * 作者
     */
    private String author;

    /**
     * 点赞
     */
    private Integer starNum;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * url
     */
    private String url;

    /**
     * ip
     */
    private String ip;

    /**
     * 代理
     */
    private String agent;

    /**
     * 文本
     */
    private String text;

    /**
     * 类型(1:评论:2:回复)
     */
    private CommentType type;

    /**
     * 状态(1:通过;2:待审核；3屏蔽)
     */
    private CommentStatus status;
}
