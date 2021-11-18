package com.dandandog.blog.modules.admin.content.web.faces.vo;

import com.dandandog.blog.modules.admin.content.entity.enums.ContentStatus;
import com.dandandog.blog.modules.admin.content.entity.enums.ContentType;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.mapstruct.model.MapperUrl;
import lombok.Data;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 11:01
 */
@Data
public class AttachmentVo implements IVo {

    private String id;

    private String parentId;

    private String title;

    private String slug;

    private MapperUrl text;

    private ContentType type = ContentType.ATTACHMENT;

    private ContentStatus status = ContentStatus.PUBLISH;
}
