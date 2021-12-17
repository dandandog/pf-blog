package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.vo.BlogAttachVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import com.dandandog.modules.blog.entity.BlogContent;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 17:01
 */
@Mapper(uses = {QualifierUrl.class})
public interface BlogAttachMapper extends IMapper<BlogContent, BlogAttachVo> {
}
