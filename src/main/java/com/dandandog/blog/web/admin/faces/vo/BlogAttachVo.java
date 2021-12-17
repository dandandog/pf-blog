package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.modules.blog.entity.enums.ContentState;
import com.dandandog.modules.blog.entity.enums.ContentType;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.mapstruct.model.MapperUrl;
import lombok.Data;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 11:01
 */
@Data
public class BlogAttachVo implements IVo {

    private String id;

    private String parentId;

    private String title;

    private String slug;

    private MapperUrl text;

    private ContentType type = ContentType.ATTACHMENT;

    private ContentState state = ContentState.PUBLISH;
}
