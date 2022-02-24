package com.dandandog.modules.blog.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.modules.blog.entity.enums.CommentState;
import com.dandandog.modules.blog.entity.enums.CommentType;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 评论(BlogComments)表实体类
 *
 * @author Johnny
 * @since 2021-09-09 14:40:53
 */
@Getter
@Setter
@TableName("blog_comment")
public class BlogComment extends BaseEntity {


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
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 点赞
     */
    private Integer starNum;

    /**
     * 邮箱
     */
    private String mail;

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
    private CommentState state;


    /**
     * 创建时间
     */
    @TableField(
            fill = FieldFill.INSERT
    )
    protected LocalDateTime createdTime;
}
