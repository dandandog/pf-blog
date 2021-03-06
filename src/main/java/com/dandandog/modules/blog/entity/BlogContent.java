package com.dandandog.modules.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.modules.blog.entity.enums.ContentState;
import com.dandandog.modules.blog.entity.enums.ContentType;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 内容(BlogContents)表实体类
 *
 * @author Johnny
 * @since 2021-09-09 14:40:58
 */
@Getter
@Setter
@TableName("blog_content")
public class BlogContent extends AuditableEntity {


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
    private int seq;

    /**
     * 模板
     */
    private String template;

    /**
     * 类型(1:页面;2:附件:3:帖子)
     */
    private ContentType type;

    /**
     * 状态(1:发布; 2:未发布)
     */
    private ContentState state;

    /**
     * 密码
     */
    private String password;

    /**
     * 浏览数量
     */
    private int viewNum;

    /**
     * 评论数量
     */
    private int commentsNum;

    /**
     * 允许评论
     */
    private boolean allowComment;

    /**
     * 允许引用
     */
    private boolean allowPing;

    /**
     * 允许聚合
     */
    private boolean allowFeed;


}
