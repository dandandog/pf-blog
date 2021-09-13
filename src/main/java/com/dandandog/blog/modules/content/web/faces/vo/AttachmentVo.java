package com.dandandog.blog.modules.content.web.faces.vo;

import com.dandandog.blog.modules.content.entity.enums.ContentType;
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

    private ContentType type;

    private AttachmentVo() {
        this.type = ContentType.POST;
    }
}
