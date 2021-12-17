package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.vo.BlogTagVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.modules.blog.entity.BlogMeta;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 16:55
 */
@Mapper
public interface BlogTagMapper extends IMapper<BlogMeta, BlogTagVo> {
}
