package com.dandandog.blog.modules.admin.content.web.faces.vo;

import com.dandandog.blog.modules.admin.content.entity.enums.ContentType;
import com.dandandog.framework.mapstruct.model.MapperUrl;
import com.dandandog.framework.mapstruct.model.MapperVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 11:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AttachmentVo extends MapperVo {

    private String parentId;

    private String title;

    private String slug;

    private MapperUrl text;

    private ContentType type;

    public ContentType getType() {
        return ContentType.ATTACHMENT;
    }
}
