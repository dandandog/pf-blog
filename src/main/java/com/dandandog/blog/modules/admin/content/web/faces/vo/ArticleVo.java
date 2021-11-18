package com.dandandog.blog.modules.admin.content.web.faces.vo;

import com.dandandog.blog.modules.admin.content.entity.enums.ContentStatus;
import com.dandandog.blog.modules.admin.content.entity.enums.ContentType;
import com.dandandog.framework.common.model.IVo;
import com.google.common.collect.Lists;
import lombok.Data;
import org.primefaces.model.TreeNode;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 18:03
 */
@Data
public class ArticleVo implements IVo {

    private String id;

    private String title;

    private String slug;

    private String text;

    private int seq;

    private String template;

    private ContentType type = ContentType.POST;

    private ContentStatus status = ContentStatus.PUBLISH;

    private String password;

    private boolean allowComment = true;

    private boolean allowPing = true;

    private boolean allowFeed = true;

    private String creator;

    private LocalDateTime operatedTime;

    private TreeNode categoryNode;

    private List<String> tags;

    private Collection<AttachmentVo> attachments = Lists.newArrayList();

}
