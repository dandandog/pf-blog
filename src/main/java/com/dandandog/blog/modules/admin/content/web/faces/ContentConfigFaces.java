package com.dandandog.blog.modules.admin.content.web.faces;

import cn.hutool.core.map.MapUtil;
import com.dandandog.blog.modules.admin.content.entity.BlogContentConfigs;
import com.dandandog.blog.modules.admin.content.service.BlogContentConfigsService;
import com.dandandog.blog.modules.admin.website.entity.DictValue;
import com.dandandog.blog.modules.admin.website.web.faces.vo.DictVo;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.mapstruct.utils.MapperUtil;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/27 12:31
 */
@Faces
public class ContentConfigFaces {

    @Resource
    private BlogContentConfigsService contentConfigsService;

    public Map<DictVo, BlogContentConfigs> findByValue(Collection<DictValue> values, String contentId) {
        Map<DictVo, BlogContentConfigs> data = values.stream().collect(Collectors.toMap(dictValue -> MapperUtil.map(dictValue, DictVo.class), o ->
                contentConfigsService.lambdaQuery().eq(BlogContentConfigs::getName, o.getCode())
                        .eq(BlogContentConfigs::getContentId, contentId)
                        .oneOpt().orElse(new BlogContentConfigs(o.getCode(), null, contentId))));
        return MapUtil.sort(data, Comparator.comparingInt(DictVo::getSeq));
    }
}
