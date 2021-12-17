package com.dandandog.blog.web.admin.controller;


import cn.hutool.core.collection.CollUtil;
import com.dandandog.blog.web.admin.faces.BlogCommentFaces;
import com.dandandog.blog.web.admin.faces.vo.BlogCommentVo;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.modules.blog.entity.enums.CommentState;
import com.google.common.collect.Lists;
import org.primefaces.model.LazyDataModel;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论(BlogComments)表控制层
 *
 * @author Johnny
 * @since 2021-09-09 14:40:57
 */
@Controller("/admin/content/comment.faces")
public class BlogCommentController extends FacesController {

    @Resource
    private BlogCommentFaces commentFaces;

    @Override
    public void onEntry() {
        putViewScope("dataModel", commentFaces.findDataModel());
        putViewScope("vo", new BlogCommentVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());
        putViewScope("statuses", CommentState.values());
    }


    public LazyDataModel<BlogCommentVo> getDataModel() {
        return getViewScope("dataModel");
    }

    public void add() {
        putViewScope("vo", new BlogCommentVo());
    }

    public void edit() {
        BlogCommentVo selected = getViewScope("sinSelected");
        BlogCommentVo vo = commentFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        BlogCommentVo vo = getViewScope("vo");
        commentFaces.saveOrUpdate(vo);
    }

    @MessageRequired(type = MessageType.DOWNLOAD)
    public void delete() {
        BlogCommentVo selected = getViewScope("sinSelected");
        List<BlogCommentVo> selectedList = getViewScope("mulSelected");
        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(IVo::getId).toArray(String[]::new);
        commentFaces.removeByIds(idList);
    }


}
