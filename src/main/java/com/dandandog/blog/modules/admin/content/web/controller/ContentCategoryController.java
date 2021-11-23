package com.dandandog.blog.modules.admin.content.web.controller;


import com.dandandog.blog.common.utils.TreeUtil;
import com.dandandog.blog.modules.admin.content.entity.BlogMetas;
import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.blog.modules.admin.content.web.faces.MetasFaces;
import com.dandandog.blog.modules.admin.content.web.faces.vo.CategoryVo;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeParams;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 元数据(BlogMetas)表控制层
 *
 * @author Johnny
 * @since 2021-09-09 14:41:00
 */
@Controller("/admin/content/category.faces")
public class ContentCategoryController extends FacesController {

    @Resource
    private MetasFaces metasFaces;

    @Override
    public void onEntry() {
        putViewScope("rootTable", getDataModel(null));

        putViewScope("vo", new CategoryVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", new TreeNode[0]);
    }

    public TreeNode getDataModel(CategoryVo vo) {
        TreeDataModel dataModel = getViewScope("dataModel");
        TreeParams params = new TreeParams();
        if (dataModel == null) {
            dataModel = metasFaces.findDataModel(CategoryVo.class);
        }
        if (vo != null) {
            params.setSelectable(new String[] {vo.getId()});
            params.setSelected(new String[] {vo.getParentId()});
        }
        putViewScope("dataModel", dataModel);
        return dataModel.createRoot(params);
    }


    public void add() {
        CategoryVo vo = new CategoryVo();
        putViewScope("vo", vo);
        putViewScope("rootTree", getDataModel(null));
    }

    public void edit() {
        BlogMetas selected = getViewScope("sinSelected");
        IVo vo = metasFaces.getOptById(selected.getId(), MetaType.CATEGORY)
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
        putViewScope("rootTree", getDataModel((CategoryVo) vo));
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        CategoryVo vo = getViewScope("vo");
        metasFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        CategoryVo selected = getViewScope("sinSelected");
        TreeNode[] mulSelected = getViewScope("mulSelected");
        String[] idList = TreeUtil.selectedId(mulSelected, selected);
        metasFaces.removeByIds(idList);
        onEntry();
    }

}
