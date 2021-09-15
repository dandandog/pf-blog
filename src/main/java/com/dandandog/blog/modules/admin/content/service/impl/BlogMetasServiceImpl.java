package com.dandandog.blog.modules.admin.content.service.impl;

import com.dandandog.blog.modules.admin.content.dao.BlogMetasDao;
import com.dandandog.blog.modules.admin.content.entity.BlogMetas;
import com.dandandog.blog.modules.admin.content.entity.enums.MetaType;
import com.dandandog.blog.modules.admin.content.service.BlogMetasService;
import com.dandandog.framework.core.entity.BaseEntity;
import org.springframework.stereotype.Service;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;

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
public class BlogMetasServiceImpl extends BaseServiceImpl<BlogMetasDao, BlogMetas> implements BlogMetasService {

    @Override
    public List<BlogMetas> checkAndSaveTags(String... tags) {
        return Arrays.stream(tags).map(tag -> {
            Optional<BlogMetas> optMetas = lambdaQuery().eq(BlogMetas::getName, tag).eq(BlogMetas::getType, MetaType.TAG).oneOpt();
            return optMetas.orElseGet(() -> {
                BlogMetas meta = new BlogMetas();
                meta.setType(MetaType.TAG);
                meta.setName(tag);
                meta.setSlug(tag);
                save(meta);
                return meta;
            });
        }).collect(Collectors.toList());
    }
}
