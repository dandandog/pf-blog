package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.vo.BlogArticleVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.modules.blog.entity.BlogContent;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 11:37
 */
@Mapper
public interface BlogArticleMapper extends IMapper<BlogContent, BlogArticleVo> {

}
