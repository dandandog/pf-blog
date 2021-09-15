package com.dandandog.blog.modules.admin.content.web.faces.mapper;

import com.dandandog.blog.modules.admin.content.entity.BlogContents;
import com.dandandog.blog.modules.admin.content.web.faces.vo.ArticleVo;
import com.dandandog.framework.mapstruct.IMapper;
import org.mapstruct.Mapper;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 11:37
 */
@Mapper
public interface ArticleMapper extends IMapper<BlogContents, ArticleVo> {

}
