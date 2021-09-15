package com.dandandog.blog.modules.admin.content.web.faces;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.blog.common.adapter.DefaultTreeAdapter;
import com.dandandog.blog.common.model.MapperTree;
import com.dandandog.blog.modules.admin.content.entity.BlogMetas;
import com.dandandog.blog.modules.admin.content.entity.BlogMetasContents;
import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.blog.modules.admin.content.service.BlogMetasContentsService;
import com.dandandog.blog.modules.admin.content.service.BlogMetasService;
import com.dandandog.blog.modules.admin.content.web.faces.vo.CategoryVo;
import com.dandandog.blog.modules.admin.content.web.faces.vo.TagVo;
import com.dandandog.framework.core.annotation.Facet;
import com.dandandog.framework.core.entity.AuditableEntity;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.dandandog.framework.mapstruct.model.MapperVo;
import org.primefaces.model.TreeNode;
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
@Facet
public class MetasFaces {

    @Resource
    private BlogMetasService metasService;

    @Resource
    private BlogMetasContentsService metasContentsService;


    private Class<? extends MapperVo> chooseClass(MetaType type) {
        return MetaType.CATEGORY.equals(type) ? CategoryVo.class : TagVo.class;
    }

    public TreeNode findDataModel(DefaultTreeAdapter<BlogMetas> treeAdapter, MapperTree vo) {
        BlogMetas entity = Optional.ofNullable(vo).map(mapperTree -> MapperUtil.map(mapperTree, BlogMetas.class)).orElse(null);
        return findDataModel(treeAdapter, entity);
    }

    public TreeNode findDataModel(DefaultTreeAdapter<BlogMetas> treeAdapter, BlogMetas entity) {
        Wrapper<BlogMetas> queryWrapper = new LambdaQueryWrapper<BlogMetas>().eq(BlogMetas::getType, MetaType.CATEGORY)
                .orderByAsc(BlogMetas::getSeq).orderByDesc(AuditableEntity::getOperatedTime);
        if (entity != null) {
            BlogMetas selected = metasService.lambdaQuery().eq(BlogMetas::getParentId, entity.getParentId()).oneOpt().orElse(null);
            return treeAdapter.getRootTree(queryWrapper, true, entity, selected);
        }
        return treeAdapter.getRootTree(queryWrapper, true, null);
    }

    public Optional<? extends MapperVo> getOptById(String id, MetaType type) {
        return Optional.ofNullable(metasService.getById(id)).map(entity -> MapperUtil.map(entity, chooseClass(type)));
    }

    @Transactional
    public void saveOrUpdate(MapperVo vo) {
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

    public Collection<? extends MapperVo> list(Wrapper<BlogMetas> wrapper, MetaType type) {
        List<BlogMetas> list = metasService.list(wrapper);
        return MapperUtil.mapToAll(list, chooseClass(type));
    }
}
