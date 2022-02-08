package com.dandandog.blog.web.admin.controller;


import com.dandandog.blog.web.admin.faces.BlogMetasFaces;
import com.dandandog.blog.web.admin.faces.vo.BlogCategoryVo;
import com.dandandog.common.utils.TreeUtil;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.modules.blog.entity.BlogMeta;
import com.dandandog.modules.blog.entity.enums.MetaType;
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
public class BlogCategoryController extends FacesController {

    @Resource
    private BlogMetasFaces metasFaces;

    @Override
    public void onEntry() {
//        putViewScope("rootTable", getDataModel(null));

        putViewScope("vo", new BlogCategoryVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", new TreeNode[0]);
    }

//    public TreeNode getDataModel(BlogCategoryVo vo) {
//        TreeDataModel dataModel = getViewScope("dataModel");
//        TreeConfig params = new TreeConfig();
//        if (dataModel == null) {
//            dataModel = metasFaces.findDataModel(BlogCategoryVo.class);
//        }
//        if (vo != null) {
//            params.setUnSelectable(new String[] {vo.getId()});
//            params.setSelected(new String[] {vo.getParentId()});
//        }
//        putViewScope("dataModel", dataModel);
//        return dataModel.createRoot(params);
//    }


    public void add() {
        BlogCategoryVo vo = new BlogCategoryVo();
        putViewScope("vo", vo);
//        putViewScope("rootTree", getDataModel(null));
    }

    public void edit() {
        BlogMeta selected = getViewScope("sinSelected");
        IVo vo = metasFaces.getOptById(selected.getId(), MetaType.CATEGORY)
                .orElseThrow(() -> new MessageResolvableException("error.dataNotFound"));
        putViewScope("vo", vo);
//        putViewScope("rootTree", getDataModel((BlogCategoryVo) vo));
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        BlogCategoryVo vo = getViewScope("vo");
        metasFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        TreeNode selected = getViewScope("sinSelected");
        TreeNode[] mulSelected = getViewScope("mulSelected");
        String[] idList = TreeUtil.selectedId(mulSelected, selected);
        metasFaces.removeByIds(idList);
        onEntry();
    }

}
