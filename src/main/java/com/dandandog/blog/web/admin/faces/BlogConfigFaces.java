package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.map.MapUtil;
import com.dandandog.blog.web.admin.faces.vo.DictVo;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.modules.blog.entity.BlogConfig;
import com.dandandog.modules.blog.entity.BlogConfigGlobal;
import com.dandandog.modules.blog.service.BlogConfigService;
import com.dandandog.modules.sys.entity.DictValue;

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
public class BlogConfigFaces {

    @Resource
    private BlogConfigService configService;

    public Map<DictVo, BlogConfig> findByValue(Collection<DictValue> values, String contentId) {
        Map<DictVo, BlogConfig> data = values.stream().collect(Collectors.toMap(dictValue -> MapperUtil.map(dictValue, DictVo.class), o ->
                configService.lambdaQuery().eq(BlogConfig::getName, o.getCode())
                        .eq(BlogConfig::getContentId, contentId)
                        .oneOpt().orElse(new BlogConfig(o.getCode(), null, contentId))));
        return MapUtil.sort(data, Comparator.comparingInt(DictVo::getSeq));
    }

    public Map<DictVo, BlogConfig> findByValue(Collection<DictValue> values) {
        Map<DictVo, BlogConfig> data = values.stream().collect(Collectors.toMap(dictValue -> MapperUtil.map(dictValue, DictVo.class), o ->
                configService.lambdaQuery().eq(BlogConfig::getName, o.getCode())
                        .oneOpt().orElse(new BlogConfig(o.getCode(), null))));
        return MapUtil.sort(data, Comparator.comparingInt(DictVo::getSeq));
    }


    public void saveOrUpdate(Collection<BlogConfigGlobal> values) {
    }
}
