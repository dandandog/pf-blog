package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.dandandog.blog.web.admin.faces.adapter.BlogMetaTreeAdapter;
import com.dandandog.blog.web.admin.faces.vo.BlogCategoryVo;
import com.dandandog.blog.web.admin.faces.vo.BlogTagVo;
import com.dandandog.common.model.MapperTreeDataModel;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.faces.model.tree.TreeFaces;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.modules.blog.entity.BlogMeta;
import com.dandandog.modules.blog.entity.BlogMetasContent;
import com.dandandog.modules.blog.entity.enums.MetaType;
import com.dandandog.modules.blog.service.BlogMetaContentsService;
import com.dandandog.modules.blog.service.BlogMetaService;
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
public class BlogMetasFaces {

    @Resource
    private BlogMetaService metasService;

    @Resource
    private BlogMetaContentsService metasContentsService;


    private Class<? extends IVo> chooseClass(MetaType type) {
        return MetaType.CATEGORY.equals(type) ? BlogCategoryVo.class : BlogTagVo.class;
    }

    public <T extends TreeFaces> TreeDataModel findDataModel(Class<T> voClass) {
        return MapperTreeDataModel.getInstance(new BlogMetaTreeAdapter(), voClass);
    }


    public Optional<? extends IVo> getOptById(String id, MetaType type) {
        return Optional.ofNullable(metasService.getById(id)).map(entity -> MapperUtil.map(entity, chooseClass(type)));
    }

    @Transactional
    public void saveOrUpdate(IVo vo) {
        BlogMeta entity = MapperUtil.map(vo, BlogMeta.class);
        if (ObjectUtil.isNotEmpty(entity.getId())) {
            Long count = metasContentsService.lambdaQuery().eq(BlogMetasContent::getMetaId, entity.getId()).count();
            entity.setCount(count.intValue());
        }
        metasService.saveOrUpdate(entity);
    }

    public void removeByIds(String[] idList) {
        metasService.removeByIds(Arrays.asList(idList));
    }

    public Collection<? extends IVo> list(Wrapper<BlogMeta> wrapper, MetaType type) {
        List<BlogMeta> list = metasService.list(wrapper);
        return MapperUtil.mapToAll(list, chooseClass(type));
    }
}
