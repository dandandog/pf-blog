package com.dandandog.blog.modules.admin.content.web.controller;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dandandog.blog.modules.admin.content.entity.enums.CommentStatus;
import com.dandandog.blog.modules.admin.content.entity.enums.ContentStatus;
import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.blog.modules.admin.content.web.faces.CommentFaces;
import com.dandandog.blog.modules.admin.content.web.faces.vo.ArticleVo;
import com.dandandog.blog.modules.admin.content.web.faces.vo.CommentVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.model.MapperVo;
import com.google.common.collect.Lists;
import org.checkerframework.checker.units.qual.C;
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
public class ContentCommentController extends FacesController {

    @Resource
    private CommentFaces commentFaces;

    @Override
    public void onEntry() {
        putViewScope("dataModel", commentFaces.findDataModel());
        putViewScope("vo", new CommentVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());
        putViewScope("statuses", CommentStatus.values());
    }


    public LazyDataModel<CommentVo> getDataModel() {
        return getViewScope("dataModel");
    }

    public void add() {
        putViewScope("vo", new CommentVo());
    }

    public void edit() {
        CommentVo selected = getViewScope("sinSelected");
        CommentVo vo = commentFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
        putViewScope("vo", vo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        CommentVo vo = getViewScope("vo");
        commentFaces.saveOrUpdate(vo);
    }

    @MessageRequired(type = MessageType.DOWNLOAD)
    public void delete() {
        CommentVo selected = getViewScope("sinSelected");
        List<CommentVo> selectedList = getViewScope("mulSelected");
        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(MapperVo::getId).toArray(String[]::new);
        commentFaces.removeByIds(idList);
    }


}
