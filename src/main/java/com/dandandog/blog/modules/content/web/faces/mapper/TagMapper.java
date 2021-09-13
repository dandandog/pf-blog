package com.dandandog.blog.modules.content.web.faces.mapper;

import com.dandandog.blog.modules.content.entity.BlogMetas;
import com.dandandog.blog.modules.content.web.faces.vo.TagVo;
import com.dandandog.framework.mapstruct.IMapper;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 16:55
 */
@Mapper
public interface TagMapper extends IMapper<BlogMetas, TagVo> {
}
