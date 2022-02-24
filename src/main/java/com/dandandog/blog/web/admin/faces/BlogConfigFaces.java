package com.dandandog.blog.web.admin.faces;

import com.dandandog.blog.web.admin.faces.vo.ConfigDictValueVo;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.modules.blog.entity.BlogConfig;
import com.dandandog.modules.blog.entity.BlogConfigGlobal;
import com.dandandog.modules.blog.service.BlogConfigService;
import com.dandandog.modules.config.entity.ConfigDictValue;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/27 12:31
 */
@Faces
public class BlogConfigFaces {

    @Resource
    private BlogConfigService configService;

    public Map<ConfigDictValueVo, BlogConfig> findByValue(Collection<ConfigDictValue> values, String contentId) {
//        Map<DictValueVo, BlogConfig> data = values.stream().collect(Collectors.toMap(dictValue -> MapperUtil.map(dictValue, DictValueVo.class), o ->
//                configService.lambdaQuery().eq(BlogConfig::getName, o.getCode())
//                        .eq(BlogConfig::getContentId, contentId)
//                        .oneOpt().orElse(new BlogConfig(o.getCode(), null, contentId))));
//        return MapUtil.sort(data, Comparator.comparingInt(DictValueVo::getSeq));
        return null;
    }

    public Map<ConfigDictValueVo, BlogConfig> findByValue(Collection<ConfigDictValue> values) {
//        Map<DictValueVo, BlogConfig> data = values.stream().collect(Collectors.toMap(dictValue -> MapperUtil.map(dictValue, DictValueVo.class), o ->
//                configService.lambdaQuery().eq(BlogConfig::getName, o.getCode())
//                        .oneOpt().orElse(new BlogConfig(o.getCode(), null))));
//        return MapUtil.sort(data, Comparator.comparingInt(DictValueVo::getSeq));
        return null;
    }


    public void saveOrUpdate(Collection<BlogConfigGlobal> values) {
    }
}
