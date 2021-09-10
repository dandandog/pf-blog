package com.dandandog.blog.modules.content.web.controller;


import com.dandandog.blog.common.model.MapperPageDataModel;
import com.dandandog.blog.modules.content.web.faces.adapter.ContentAdapter;
import com.dandandog.blog.modules.content.web.faces.vo.ArticleVo;
import com.dandandog.blog.modules.content.web.faces.vo.TagVo;
import com.dandandog.framework.faces.adapter.PageDataModel;
import com.dandandog.framework.faces.controller.FacesController;
import com.google.common.collect.Lists;
import org.primefaces.model.LazyDataModel;
import org.springframework.stereotype.Controller;

/**
 * 内容(BlogContents)表控制层
 *
 * @author Johnny
 * @since 2021-09-09 14:40:59
 */
@Controller("/content/article.faces")
public class ContentArticleController extends FacesController {


    @Override
    public void onEntry() {
        putViewScope("dataModel", MapperPageDataModel.getInstance(new ContentAdapter(), ArticleVo.class));
        putViewScope("vo", new ArticleVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", Lists.newArrayList());
    }

    public LazyDataModel<ArticleVo> getDataModel() {
        return getViewScope("dataModel");
    }


    public void add() {

    }

    public void edit() {


    }

    public void save() {

    }

    public void delete() {

    }


}
