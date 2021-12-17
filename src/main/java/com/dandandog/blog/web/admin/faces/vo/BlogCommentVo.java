package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.modules.blog.entity.enums.CommentState;
import com.dandandog.modules.blog.entity.enums.CommentType;
import com.dandandog.framework.common.model.IVo;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/16 15:17
 */
@Data
public class BlogCommentVo implements IVo {

    private String id;

    private BlogArticleVo content;

    private String author;

    private int starNum;

    private String mail;

    private String avatarUrl;

    private String ip;

    private String agent;

    private String text;

    private CommentType type;

    private CommentState status;

    private LocalDateTime createdTime;

    private BlogCommentVo parent;

}
