package com.dandandog.blog.modules.content.web.faces;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.dandandog.blog.modules.content.entity.BlogMetas;
import com.dandandog.blog.modules.content.entity.BlogMetasContents;
import com.dandandog.blog.modules.content.entity.enums.MetaType;
import com.dandandog.blog.modules.content.service.BlogMetasContentsService;
import com.dandandog.blog.modules.content.service.BlogMetasService;
import com.dandandog.blog.modules.content.web.faces.vo.CategoryVo;
import com.dandandog.blog.modules.content.web.faces.vo.TagVo;
import com.dandandog.framework.common.model.IEntity;
import com.dandandog.framework.core.annotation.Facet;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.dandandog.framework.mapstruct.model.MapperVo;
import org.apache.poi.ss.formula.functions.T;
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
