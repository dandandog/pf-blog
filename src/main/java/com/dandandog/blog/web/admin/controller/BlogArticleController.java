package com.dandandog.blog.web.admin.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dandandog.blog.web.admin.faces.BlogConfigFaces;
import com.dandandog.blog.web.admin.faces.BlogContentFaces;
import com.dandandog.blog.web.admin.faces.BlogMetasFaces;
import com.dandandog.blog.web.admin.faces.DictFaces;
import com.dandandog.blog.web.admin.faces.vo.BlogArticleVo;
import com.dandandog.blog.web.admin.faces.vo.BlogAttachVo;
import com.dandandog.blog.web.admin.faces.vo.BlogCategoryVo;
import com.dandandog.blog.web.admin.faces.vo.DictValueVo;
import com.dandandog.common.utils.UploadUtil;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.faces.model.tree.TreeConfig;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import com.dandandog.modules.blog.entity.BlogConfig;
import com.dandandog.modules.blog.entity.BlogConfigContent;
import com.dandandog.modules.blog.entity.enums.ContentState;
import com.dandandog.modules.blog.entity.enums.MetaType;
import com.dandandog.modules.sys.entity.DictValue;
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
public class BlogArticleController extends FacesController {

    @Resource
    private BlogMetasFaces metasFaces;

    @Resource
    private BlogContentFaces contentFaces;

    @Resource
    private DictFaces dictFaces;

    @Resource
    private BlogConfigFaces contentConfigFaces;

    @Override
    public void onEntry() {
        putViewScope("dataModel", contentFaces.findDataModel());
        putViewScope("vo", new BlogArticleVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());

        putViewScope("tags", metasFaces.list(Wrappers.emptyWrapper(), MetaType.TAG));
        putViewScope("statuses", ContentState.values());
    }

    public Map<DictValueVo, BlogConfig> findFields(String contentId) {
        String KEYS = "article";
        Multimap<String, DictValue> values = dictFaces.getValueByCodes(KEYS);
        return contentConfigFaces.findByValue(values.get(KEYS), contentId);
    }

    public LazyDataModel<BlogArticleVo> getDataModel() {
        return getViewScope("dataModel");
    }

    public TreeNode getTreeDataModel(TreeFaces tree) {
        TreeDataModel dataModel = getViewScope("treeDataModel");

        if (dataModel == null) {
            dataModel = metasFaces.findDataModel(BlogCategoryVo.class);
        }
        TreeConfig params = new TreeConfig();
        if (tree != null) {
            params.setUnSelectable(new String[] {tree.getId()});
            params.setSelected(new String[] {tree.getParentId()});
        }
        putViewScope("treeDataModel", dataModel);
        return dataModel.createRoot(params);
    }


    public void add() {
        BlogArticleVo vo = new BlogArticleVo();
        putViewScope("vo", vo);
        putViewScope("categories", getTreeDataModel(null));
        putViewScope("fields", findFields(null));
    }

    public void edit() {
        BlogArticleVo selected = getViewScope("sinSelected");
        BlogArticleVo vo = contentFaces.getOptById(selected.getId())
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        Map<DictValueVo, BlogConfig> fields = findFields(vo.getId());
        putViewScope("vo", vo);
        putViewScope("categories", getTreeDataModel((TreeFaces) vo.getCategoryNode().getData()));
        putViewScope("fields", fields);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        BlogArticleVo vo = getViewScope("vo");
        Map<DictValueVo, BlogConfigContent> fields = getViewScope("fields");
        contentFaces.saveOrUpdate(vo, fields.values());
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        BlogArticleVo selected = getViewScope("sinSelected");
        List<BlogArticleVo> selectedList = getViewScope("mulSelected");
        String[] idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(IVo::getId).toArray(String[]::new);
        contentFaces.removeByIds(idList);
    }

    public void onFileUpload(FileUploadEvent event) throws IOException {
        BlogArticleVo vo = getViewScope("vo");
        UploadedFile file = event.getFile();
        BlogAttachVo attachmentVo = new BlogAttachVo();
        attachmentVo.setTitle(file.getFileName());
        attachmentVo.setSlug(StrUtil.replace(file.getFileName(), StrUtil.DOT, StrUtil.DASHED));
        attachmentVo.setText(UploadUtil.componentUpload(file));
        vo.getAttachments().add(attachmentVo);
    }

    public void onFileRemove(int index) {
        BlogArticleVo vo = getViewScope("vo");
        BlogAttachVo entity = CollUtil.get(vo.getAttachments(), index);
        vo.getAttachments().remove(entity);
    }


}
