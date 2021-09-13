package com.dandandog.blog.modules.content.web.faces.mapper;

import com.dandandog.blog.modules.content.entity.BlogContents;
import com.dandandog.blog.modules.content.web.faces.vo.AttachmentVo;
import com.dandandog.framework.mapstruct.IMapper;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 17:01
 */
@Mapper
public interface AttachmentMapper extends IMapper<BlogContents, AttachmentVo> {
}
