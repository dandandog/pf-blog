package com.dandandog.blog.modules.admin.content.web.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dandandog.blog.common.adapter.DefaultTreeAdapter;
import com.dandandog.blog.common.utils.UploadUtil;
import com.dandandog.blog.modules.admin.content.entity.BlogMetas;
import com.dandandog.blog.modules.admin.content.entity.enums.ContentStatus;
import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.blog.modules.admin.content.web.faces.ContentFaces;
import com.dandandog.blog.modules.admin.content.web.faces.MetasFaces;
import com.dandandog.blog.modules.admin.content.web.faces.vo.ArticleVo;
import com.dandandog.blog.modules.admin.content.web.faces.vo.AttachmentVo;
import com.dandandog.blog.modules.admin.content.web.faces.vo.CategoryVo;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.model.MapperVo;
import com.google.common.collect.Lists;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 内容(BlogContents)表控制层
 *
 * @author Johnny
 * @since 2021-09-09 14:40:59
 */
@Controller("/admin/content/article.faces")
public class ContentArticleController extends FacesController {

    @Resource
    private MetasFaces metasFaces;

    @Resource
    private ContentFaces contentFaces;


    @Override
    public void onEntry() {
        putViewScope("dataModel", contentFaces.findDataModel());
        putViewScope("vo", new ArticleVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());

        putViewScope("categories", buildTree(null));
        putViewScope("tags", metasFaces.list(Wrappers.emptyWrapper(), MetaType.TAG));
        putViewScope("statuses", ContentStatus.values());
    }

    public LazyDataModel<ArticleVo> getDataModel() {
        return getViewScope("dataModel");
    }

    public void add() {
        ArticleVo vo = new ArticleVo();
        putViewScope("vo", vo);
    }

    public void edit() {
        ArticleVo selected = getViewScope("sinSelected");
        ArticleVo vo = contentFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
        putViewScope("vo", vo);
    }

    public void save() {
        ArticleVo vo = getViewScope("vo");
        contentFaces.saveOrUpdate(vo);
    }

    public void delete() {
        ArticleVo selected = getViewScope("sinSelected");
        List<ArticleVo> selectedList = getViewScope("mulSelected");
        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(MapperVo::getId).toArray(String[]::new);
        contentFaces.removeByIds(idList);
    }

    public void upload(FileUploadEvent event) throws IOException {
        ArticleVo vo = getViewScope("vo");
        UploadedFile file = event.getFile();
        AttachmentVo attachmentVo = new AttachmentVo();
        attachmentVo.setTitle(file.getFileName());
        attachmentVo.setSlug(StrUtil.replace(file.getFileName(), StrUtil.DOT, StrUtil.DASHED));
        attachmentVo.setText(UploadUtil.componentUpload(file));
        vo.getAttachments().add(attachmentVo);
    }

    private TreeNode buildTree(CategoryVo vo) {
        DefaultTreeAdapter<BlogMetas> adapter = getViewScope("adapter");
        if (adapter == null) {
            adapter = new DefaultTreeAdapter<>(BlogMetas.class);
            putViewScope("adapter", adapter);
        }
        return metasFaces.findDataModel(adapter, vo);
    }
}
