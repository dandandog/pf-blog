package com.dandandog.modules.blog.service.impl;

import com.dandandog.modules.blog.dao.BlogMetaContentDao;
import com.dandandog.modules.blog.entity.BlogMeta;
import com.dandandog.modules.blog.entity.BlogMetasContent;
import com.dandandog.modules.blog.service.BlogMetaContentsService;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 元数据关联内容 (BlogMetasContents)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-09 14:41:01
 */
@Service
public class BlogMetaContentsServiceImpl extends BaseServiceImpl<BlogMetaContentDao, BlogMetasContent> implements BlogMetaContentsService {

    @Override
    public List<BlogMeta> findByContentId(String contentId) {
        return baseMapper.getMetasByContent(contentId);
    }
}
