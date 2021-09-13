package com.dandandog.blog.modules.content.web.faces.vo;

import com.dandandog.blog.modules.content.entity.enums.ContentStatus;
import com.dandandog.blog.modules.content.entity.enums.ContentType;
import com.dandandog.framework.mapstruct.model.MapperVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.primefaces.model.TreeNode;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 18:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleVo extends MapperVo {

    private String title;

    private String slug;

    private String text;

    private int seq;

    private String template;

    private ContentType type;

    private ContentStatus status = ContentStatus.PUBLISH;

    private String password;

    private boolean allowComment;

    private boolean allowPing;

    private boolean allowFeed;

    private String creator;

    private LocalDateTime operatedTime;

    private TreeNode categoryNode;

    private List<String> tags;

    private Collection<AttachmentVo> attachments;

    private ArticleVo() {
        this.type = ContentType.POST;
    }

}
