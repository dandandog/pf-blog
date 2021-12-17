package com.dandandog.blog.web.admin.faces;

import com.dandandog.blog.web.admin.faces.adapter.BlogCommentAdapter;
import com.dandandog.blog.web.admin.faces.vo.BlogCommentVo;
import com.dandandog.common.model.MapperPageDataModel;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.modules.blog.entity.BlogComment;
import com.dandandog.modules.blog.service.BlogCommentService;
import org.primefaces.model.LazyDataModel;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/16 15:21
 */
@Faces
public class BlogCommentFaces {

    @Resource
    private BlogCommentService commentsService;


    public LazyDataModel<BlogCommentVo> findDataModel() {
        return MapperPageDataModel.getInstance(new BlogCommentAdapter(), BlogCommentVo.class);
    }

    public Optional<BlogCommentVo> getOptById(String id) {
        return Optional.ofNullable(commentsService.getById(id)).map(entity -> MapperUtil.map(entity, BlogCommentVo.class));
    }

    public void saveOrUpdate(BlogCommentVo vo) {
        BlogComment entity = MapperUtil.map(vo, BlogComment.class);
        commentsService.saveOrUpdate(entity);
    }

    public void removeByIds(String[] idList) {
        commentsService.removeByIds(Arrays.asList(idList));
    }
}
