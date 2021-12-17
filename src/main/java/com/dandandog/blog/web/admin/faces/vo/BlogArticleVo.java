package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.modules.blog.entity.enums.ContentState;
import com.dandandog.modules.blog.entity.enums.ContentType;
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
public class BlogArticleVo implements IVo {

    private String id;

    private String title;

    private String slug;

    private String text;

    private int seq;

    private String template;

    private ContentType type = ContentType.POST;

    private ContentState status = ContentState.PUBLISH;

    private String password;

    private boolean allowComment = true;

    private boolean allowPing = true;

    private boolean allowFeed = true;

    private String creator;

    private LocalDateTime operatedTime;

    private TreeNode categoryNode;

    private List<String> tags;

    private Collection<BlogAttachVo> attachments = Lists.newArrayList();

}
