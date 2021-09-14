package com.dandandog.blog.modules.admin.content.web.faces.mapper;

import com.dandandog.blog.modules.admin.content.entity.BlogContents;
import com.dandandog.blog.modules.admin.content.web.faces.vo.AttachmentVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 17:01
 */
@Mapper(uses = {QualifierUrl.class})
public interface AttachmentMapper extends IMapper<BlogContents, AttachmentVo> {
}
