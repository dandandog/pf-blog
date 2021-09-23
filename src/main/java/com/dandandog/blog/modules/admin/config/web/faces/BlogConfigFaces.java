package com.dandandog.blog.modules.admin.config.web.faces;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.dandandog.blog.modules.admin.config.entity.BlogConfigs;
import com.dandandog.blog.modules.admin.config.service.BlogConfigsService;
import com.dandandog.blog.modules.admin.setting.entity.DictValue;
import com.dandandog.framework.core.annotation.Facet;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
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

    public Map<String, BlogConfigs> findByValue(Collection<DictValue> values) {
        return values.stream().collect(Collectors.toMap(DictValue::getValue, o ->
                configsService.lambdaQuery().eq(BlogConfigs::getName, o.getValue())
                        .oneOpt().orElse(new BlogConfigs(o.getValue(), null))));
    }

    public void saveOrUpdate(Collection<BlogConfigs> values) {
        configsService.saveOrUpdateBatch(values);
    }
}
