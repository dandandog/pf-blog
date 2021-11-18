package com.dandandog.blog.modules.admin.content.web.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dandandog.blog.common.utils.UploadUtil;
import com.dandandog.blog.modules.admin.content.entity.BlogContentConfigs;
import com.dandandog.blog.modules.admin.content.entity.enums.ContentStatus;
import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.blog.modules.admin.content.web.faces.ContentConfigFaces;
import com.dandandog.blog.modules.admin.content.web.faces.ContentFaces;
import com.dandandog.blog.modules.admin.content.web.faces.MetasFaces;
import com.dandandog.blog.modules.admin.content.web.faces.vo.ArticleVo;
import com.dandandog.blog.modules.admin.content.web.faces.vo.AttachmentVo;
import com.dandandog.blog.modules.admin.content.web.faces.vo.CategoryVo;
import com.dandandog.blog.modules.admin.website.entity.DictValue;
import com.dandandog.blog.modules.admin.website.web.faces.DictFaces;
import com.dandandog.blog.modules.admin.website.web.faces.vo.DictVo;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import com.dandandog.framework.faces.model.tree.TreeParams;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    @Resource
    private DictFaces dictFaces;

    @Resource
    private ContentConfigFaces contentConfigFaces;

    @Override
    public void onEntry() {
        putViewScope("dataModel", contentFaces.findDataModel());
        putViewScope("vo", new ArticleVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());

        putViewScope("tags", metasFaces.list(Wrappers.emptyWrapper(), MetaType.TAG));
        putViewScope("statuses", ContentStatus.values());
    }

    public Map<DictVo, BlogContentConfigs> findFields(String contentId) {
        String KEYS = "article";
        Multimap<String, DictValue> values = dictFaces.getValueByCodes(KEYS);
        return contentConfigFaces.findByValue(values.get(KEYS), contentId);
    }

    public LazyDataModel<ArticleVo> getDataModel() {
        return getViewScope("dataModel");
    }

    public TreeNode getTreeDataModel(TreeFaces tree) {
        TreeDataModel dataModel = getViewScope("treeDataModel");

        if (dataModel == null) {
            dataModel = metasFaces.findDataModel(CategoryVo.class);
        }
        TreeParams params = new TreeParams();
        if (tree != null) {
            params.setSelectable(new String[] {tree.getId()});
            params.setSelected(new String[] {tree.getParentId()});
        }
        putViewScope("treeDataModel", dataModel);
        return dataModel.createRoot(params);
    }


    public void add() {
        ArticleVo vo = new ArticleVo();
        putViewScope("vo", vo);
        putViewScope("categories", getTreeDataModel(null));
        putViewScope("fields", findFields(null));
    }

    public void edit() {
        ArticleVo selected = getViewScope("sinSelected");
        ArticleVo vo = contentFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        Map<DictVo, BlogContentConfigs> fields = findFields(vo.getId());
        putViewScope("vo", vo);
        putViewScope("categories", getTreeDataModel((TreeFaces) vo.getCategoryNode().getData()));
        putViewScope("fields", fields);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        ArticleVo vo = getViewScope("vo");
        Map<DictVo, BlogContentConfigs> fields = getViewScope("fields");
        contentFaces.saveOrUpdate(vo, fields.values());
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        ArticleVo selected = getViewScope("sinSelected");
        List<ArticleVo> selectedList = getViewScope("mulSelected");
        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(IVo::getId).toArray(String[]::new);
        contentFaces.removeByIds(idList);
    }

    public void onFileUpload(FileUploadEvent event) throws IOException {
        ArticleVo vo = getViewScope("vo");
        UploadedFile file = event.getFile();
        AttachmentVo attachmentVo = new AttachmentVo();
        attachmentVo.setTitle(file.getFileName());
        attachmentVo.setSlug(StrUtil.replace(file.getFileName(), StrUtil.DOT, StrUtil.DASHED));
        attachmentVo.setText(UploadUtil.componentUpload(file));
        vo.getAttachments().add(attachmentVo);
    }

    public void onFileRemove(int index) {
        ArticleVo vo = getViewScope("vo");
        AttachmentVo entity = CollUtil.get(vo.getAttachments(), index);
        vo.getAttachments().remove(entity);
    }


}
