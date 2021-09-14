package com.dandandog.blog.modules.admin.content.web.faces;

import cn.hutool.core.util.ArrayUtil;
import com.dandandog.blog.common.model.MapperPageDataModel;
import com.dandandog.blog.modules.admin.content.entity.BlogContents;
import com.dandandog.blog.modules.admin.content.entity.BlogMetas;
import com.dandandog.blog.modules.admin.content.entity.BlogMetasContents;
import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.blog.modules.admin.content.service.BlogContentsService;
import com.dandandog.blog.modules.admin.content.service.BlogMetasContentsService;
import com.dandandog.blog.modules.admin.content.service.BlogMetasService;
import com.dandandog.blog.modules.admin.content.web.faces.adapter.ContentAdapter;
import com.dandandog.blog.modules.admin.content.web.faces.vo.ArticleVo;
import com.dandandog.framework.core.annotation.Facet;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.google.common.collect.Lists;
import org.primefaces.model.LazyDataModel;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 9:58
 */
@Facet
public class ContentFaces {

    @Resource
    private BlogContentsService contentsService;

    @Resource
    private BlogMetasService metasService;

    @Resource
    private BlogMetasContentsService metasContentsService;

    public LazyDataModel<ArticleVo> findDataModel() {
        return MapperPageDataModel.getInstance(new ContentAdapter(), ArticleVo.class);
    }

    public Optional<ArticleVo> getOptById(String id) {
        return Optional.ofNullable(contentsService.getById(id)).map(entity -> MapperUtil.map(entity, ArticleVo.class));
    }

    @Transactional
    public void saveOrUpdate(ArticleVo vo) {
        BlogContents entity = MapperUtil.map(vo, BlogContents.class);
        if (entity.getId() != null) {
            contentsService.lambdaUpdate().eq(BlogContents::getParentId, entity.getId()).remove();
            metasContentsService.lambdaUpdate().eq(BlogMetasContents::getContentId, entity.getId()).remove();
        }
        // Attachment
        Collection<BlogContents> entities = Lists.newArrayList(entity);
        entities.addAll(MapperUtil.mapFromAll(vo.getAttachments(), BlogContents.class));
        contentsService.saveOrUpdateBatch(entities);

        // Metas
        List<String> tagIds = metasService.checkAndSaveTags(ArrayUtil.toArray(vo.getTags(), String.class));
        List<BlogMetasContents> metasContents = tagIds.stream().map(tagId -> new BlogMetasContents(entity.getId(), tagId)).collect(Collectors.toList());
        BlogMetas cate = (BlogMetas) vo.getCategoryNode().getData();
        metasContents.add(new BlogMetasContents(entity.getId(), cate.getId()));
        metasContentsService.saveBatch(metasContents);
    }


    public void removeByIds(String[] idList) {
        for (String id : idList) {
            contentsService.lambdaUpdate().eq(BlogContents::getParentId, id).or().eq(BaseEntity::getId, id).remove();
        }
    }


}
