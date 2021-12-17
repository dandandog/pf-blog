package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.BlogCommentFaces;
import com.dandandog.blog.web.admin.faces.BlogContentFaces;
import com.dandandog.blog.web.admin.faces.vo.BlogArticleVo;
import com.dandandog.blog.web.admin.faces.vo.BlogCommentVo;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.modules.blog.entity.BlogComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.annotation.Resource;
import java.util.Optional;

@Mapper
public abstract class BlogCommentMapper implements IMapper<BlogComment, BlogCommentVo> {

    @Resource
    private BlogContentFaces contentFaces;

    @Resource
    private BlogCommentFaces commentFaces;

    @Override
    @Mapping(target = "content", source = "contentId", qualifiedByName = "getContent")
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "getParent")
    public abstract BlogCommentVo mapTo(BlogComment blogComments);

    @Named("getContent")
    public BlogArticleVo getContent(String contentId) {
        return contentFaces.getOptById(contentId).orElse(null);
    }

    @Named("getContent")
    public String getContent(BlogArticleVo vo) {
        return Optional.ofNullable(vo).map(IVo::getId).orElse(null);
    }

    @Named("getParent")
    public BlogCommentVo getParent(String parentId) {
        return commentFaces.getOptById(parentId).orElse(null);
    }

    @Named("getParent")
    public String getParent(BlogCommentVo vo) {
        return Optional.ofNullable(vo).map(IVo::getId).orElse(null);
    }

}
