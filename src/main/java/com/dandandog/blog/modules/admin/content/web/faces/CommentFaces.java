package com.dandandog.blog.modules.admin.content.web.faces;

import com.dandandog.blog.common.model.MapperPageDataModel;
import com.dandandog.blog.modules.admin.content.entity.BlogComments;
import com.dandandog.blog.modules.admin.content.service.BlogCommentsService;
import com.dandandog.blog.modules.admin.content.web.faces.adapter.CommentAdapter;
import com.dandandog.blog.modules.admin.content.web.faces.adapter.ContentAdapter;
import com.dandandog.blog.modules.admin.content.web.faces.vo.ArticleVo;
import com.dandandog.blog.modules.admin.content.web.faces.vo.CommentVo;
import com.dandandog.framework.core.annotation.Facet;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.mapstruct.MapperUtil;
import org.primefaces.model.LazyDataModel;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/16 15:21
 */
@Facet
public class CommentFaces {

    @Resource
    private BlogCommentsService commentsService;


    public LazyDataModel<CommentVo> findDataModel() {
        return MapperPageDataModel.getInstance(new CommentAdapter(), CommentVo.class);
    }

    public Optional<CommentVo> getOptById(String id) {
        return Optional.ofNullable(commentsService.getById(id)).map(entity -> MapperUtil.map(entity, CommentVo.class));
    }

    public void saveOrUpdate(CommentVo vo) {
        BlogComments entity = MapperUtil.map(vo, BlogComments.class);
        commentsService.saveOrUpdate(entity);
    }

    public void removeByIds(String[] idList) {
        commentsService.removeByIds(Arrays.asList(idList));
    }
}
