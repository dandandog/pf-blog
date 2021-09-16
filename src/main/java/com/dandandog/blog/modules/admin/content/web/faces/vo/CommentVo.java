package com.dandandog.blog.modules.admin.content.web.faces.vo;

import com.dandandog.blog.modules.admin.content.entity.enums.CommentStatus;
import com.dandandog.blog.modules.admin.content.entity.enums.CommentType;
import com.dandandog.framework.mapstruct.model.MapperVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/16 15:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentVo extends MapperVo {

    private ArticleVo content;

    private String author;

    private int starNum;

    private String mail;

    private String url;

    private String ip;

    private String agent;

    private String text;

    private CommentType type;

    private CommentStatus status;

}
