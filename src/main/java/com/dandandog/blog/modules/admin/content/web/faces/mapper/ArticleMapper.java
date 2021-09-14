package com.dandandog.blog.modules.admin.content.web.faces.mapper;

import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.modules.admin.content.entity.BlogContents;
import com.dandandog.blog.modules.admin.content.service.BlogContentsService;
import com.dandandog.blog.modules.admin.content.web.faces.vo.ArticleVo;
import com.dandandog.blog.modules.admin.content.web.faces.vo.AttachmentVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mapstruct.MapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 11:37
 */
@Mapper
public abstract class ArticleMapper implements IMapper<BlogContents, ArticleVo> {

    @Resource
    private BlogContentsService contentsService;


    @Override
    @Mapping(target = "attachments", source = "parentId", qualifiedByName = "findAttachments")
    public abstract ArticleVo mapTo(BlogContents contents);

    @Named("findAttachments")
    public Collection<AttachmentVo> findAttachments(String parentId) {
        List<BlogContents> entities = contentsService.lambdaQuery().eq(BlogContents::getParentId, parentId).list();
        return MapperUtil.mapToAll(entities, AttachmentVo.class);
    }

    @Named("findAttachments")
    public String findAttachments(Collection<AttachmentVo> vos) {
        return CollUtil.isNotEmpty(vos) ? CollUtil.get(vos, 0).getParentId() : null;
    }
}
