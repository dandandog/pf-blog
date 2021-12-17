package com.dandandog.modules.blog.service.impl;

import com.dandandog.modules.blog.dao.BlogMetaDao;
import com.dandandog.modules.blog.entity.BlogMeta;
import com.dandandog.modules.blog.entity.enums.MetaType;
import com.dandandog.modules.blog.service.BlogMetaService;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 元数据(BlogMetas)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-09 14:41:00
 */
@Service
public class BlogMetaServiceImpl extends BaseServiceImpl<BlogMetaDao, BlogMeta> implements BlogMetaService {

    @Override
    public List<BlogMeta> checkAndSaveTags(String... tags) {
        return Arrays.stream(tags).map(tag -> {
            Optional<BlogMeta> optMetas = lambdaQuery().eq(BlogMeta::getName, tag).eq(BlogMeta::getType, MetaType.TAG).oneOpt();
            return optMetas.orElseGet(() -> {
                BlogMeta meta = new BlogMeta();
                meta.setType(MetaType.TAG);
                meta.setName(tag);
                meta.setSlug(tag);
                save(meta);
                return meta;
            });
        }).collect(Collectors.toList());
    }
}
