package com.dandandog.blog.modules.admin.content.web.faces;

import com.dandandog.blog.common.model.MapperPageDataModel;
import com.dandandog.blog.modules.admin.content.entity.BlogContents;
import com.dandandog.blog.modules.admin.content.service.BlogContentsService;
import com.dandandog.blog.modules.admin.content.web.faces.adapter.ContentAdapter;
import com.dandandog.blog.modules.admin.content.web.faces.vo.ArticleVo;
import com.dandandog.framework.core.annotation.Facet;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.mapstruct.MapperUtil;
import org.primefaces.model.LazyDataModel;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/13 9:58
 */
@Facet
public class ContentFaces {

    @Resource
    private BlogContentsService contentsService;

    public LazyDataModel<ArticleVo> findDataModel() {
        return MapperPageDataModel.getInstance(new ContentAdapter(), ArticleVo.class);
    }

    public Optional<ArticleVo> getOptById(String id) {
        return Optional.ofNullable(contentsService.getById(id)).map(entity -> MapperUtil.map(entity, ArticleVo.class));
    }

    public void saveOrUpdate(ArticleVo vo) {
        BlogContents entity = MapperUtil.map(vo, BlogContents.class);
        if (contentsService.saveOrUpdate(entity)) {
            Collection<BlogContents> entities = MapperUtil.mapFromAll(vo.getAttachments(), BlogContents.class);
            contentsService.saveOrUpdateBatch(entities);
        }
    }

    public void removeByIds(String[] idList) {
        for (String id : idList) {
            contentsService.lambdaUpdate().eq(BlogContents::getParentId, id).or().eq(BaseEntity::getId, id).remove();
        }
    }


}
