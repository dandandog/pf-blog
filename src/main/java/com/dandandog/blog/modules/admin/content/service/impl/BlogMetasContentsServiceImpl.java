package com.dandandog.blog.modules.admin.content.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.admin.content.dao.BlogMetasContentsDao;
import com.dandandog.blog.modules.admin.content.entity.BlogMetas;
import com.dandandog.blog.modules.admin.content.entity.BlogMetasContents;
import com.dandandog.blog.modules.admin.content.service.BlogMetasContentsService;
import org.springframework.stereotype.Service;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;

import java.util.List;

/**
 * 元数据关联内容 (BlogMetasContents)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-09 14:41:01
 */
@Service
public class BlogMetasContentsServiceImpl extends BaseServiceImpl<BlogMetasContentsDao, BlogMetasContents> implements BlogMetasContentsService {

    @Override
    public List<BlogMetas> findByContentId(String contentId) {
        return baseMapper.getMetasByContent(contentId);
    }
}
