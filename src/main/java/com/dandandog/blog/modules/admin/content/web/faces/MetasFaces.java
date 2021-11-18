package com.dandandog.blog.modules.admin.content.web.faces;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.dandandog.blog.common.model.MapperTreeDataModel;
import com.dandandog.blog.modules.admin.content.entity.BlogMetas;
import com.dandandog.blog.modules.admin.content.entity.BlogMetasContents;
import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.blog.modules.admin.content.service.BlogMetasContentsService;
import com.dandandog.blog.modules.admin.content.service.BlogMetasService;
import com.dandandog.blog.modules.admin.content.web.faces.adapter.MetasTreeAdapter;
import com.dandandog.blog.modules.admin.content.web.faces.vo.CategoryVo;
import com.dandandog.blog.modules.admin.content.web.faces.vo.TagVo;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 11:22
 */
@Faces
public class MetasFaces {

    @Resource
    private BlogMetasService metasService;

    @Resource
    private BlogMetasContentsService metasContentsService;


    private Class<? extends IVo> chooseClass(MetaType type) {
        return MetaType.CATEGORY.equals(type) ? CategoryVo.class : TagVo.class;
    }

    public <T extends TreeFaces> TreeDataModel findDataModel(Class<T> voClass) {
        return MapperTreeDataModel.getInstance(new MetasTreeAdapter(), voClass);
    }


    public Optional<? extends IVo> getOptById(String id, MetaType type) {
        return Optional.ofNullable(metasService.getById(id)).map(entity -> MapperUtil.map(entity, chooseClass(type)));
    }

    @Transactional
    public void saveOrUpdate(IVo vo) {
        BlogMetas entity = MapperUtil.map(vo, BlogMetas.class);
        if (ObjectUtil.isNotEmpty(entity.getId())) {
            int count = metasContentsService.lambdaQuery().eq(BlogMetasContents::getMetaId, entity.getId()).count();
            entity.setCount(count);
        }
        metasService.saveOrUpdate(entity);
    }

    public void removeByIds(String[] idList) {
        metasService.removeByIds(Arrays.asList(idList));
    }

    public Collection<? extends IVo> list(Wrapper<BlogMetas> wrapper, MetaType type) {
        List<BlogMetas> list = metasService.list(wrapper);
        return MapperUtil.mapToAll(list, chooseClass(type));
    }
}
