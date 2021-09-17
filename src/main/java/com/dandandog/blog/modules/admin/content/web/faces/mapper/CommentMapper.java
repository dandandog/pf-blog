package com.dandandog.blog.modules.admin.content.web.faces.mapper;

import cn.hutool.core.util.ObjectUtil;
import com.dandandog.blog.modules.admin.content.entity.BlogComments;
import com.dandandog.blog.modules.admin.content.web.faces.CommentFaces;
import com.dandandog.blog.modules.admin.content.web.faces.ContentFaces;
import com.dandandog.blog.modules.admin.content.web.faces.vo.ArticleVo;
import com.dandandog.blog.modules.admin.content.web.faces.vo.CommentVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.model.MapperVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.annotation.Resource;
import java.util.Optional;

@Mapper
public abstract class CommentMapper implements IMapper<BlogComments, CommentVo> {

    @Resource
    private ContentFaces contentFaces;

    @Resource
    private CommentFaces commentFaces;

    @Override
    @Mapping(target = "content", source = "contentId", qualifiedByName = "getContent")
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "getParent")
    public abstract CommentVo mapTo(BlogComments blogComments);

    @Named("getContent")
    public ArticleVo getContent(String contentId) {
        return contentFaces.getOptById(contentId).orElse(null);
    }

    @Named("getContent")
    public String getContent(ArticleVo vo) {
        return Optional.ofNullable(vo).map(MapperVo::getId).orElse(null);
    }

    @Named("getParent")
    public CommentVo getParent(String parentId) {
        return commentFaces.getOptById(parentId).orElse(null);
    }

    @Named("getParent")
    public String getParent(CommentVo vo) {
        return Optional.ofNullable(vo).map(MapperVo::getId).orElse(null);
    }

}
