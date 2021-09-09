package com.dandandog.blog.modules.blog.entity;

import com.dandandog.framework.core.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class BlogComments extends AbstractEntity {


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
    private Integer type;

    /**
     * 状态(1:批准;2:不批准)
     */
    private Integer status;
}
