package com.dandandog.blog.modules.admin.website.web.faces;

import cn.hutool.core.map.MapUtil;
import com.dandandog.blog.modules.admin.website.entity.BlogConfigs;
import com.dandandog.blog.modules.admin.website.entity.DictValue;
import com.dandandog.blog.modules.admin.website.service.BlogConfigsService;
import com.dandandog.blog.modules.admin.website.web.faces.vo.DictVo;
import com.dandandog.framework.core.annotation.Facet;
import com.dandandog.framework.mapstruct.MapperUtil;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/22 10:44
 */
@Facet
public class BlogConfigFaces {

    @Resource
    private BlogConfigsService configsService;

    public Map<DictVo, BlogConfigs> findByValue(Collection<DictValue> values) {
        Map<DictVo, BlogConfigs> data = values.stream().collect(Collectors.toMap(dictValue -> MapperUtil.map(dictValue, DictVo.class), o ->
                configsService.lambdaQuery().eq(BlogConfigs::getName, o.getCode())
                        .oneOpt().orElse(new BlogConfigs(o.getCode(), null))));
        return MapUtil.sort(data, Comparator.comparingInt(DictVo::getSeq));
    }

    public void saveOrUpdate(Collection<BlogConfigs> values) {
        configsService.saveOrUpdateBatch(values);
    }
}
