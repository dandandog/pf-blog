package com.dandandog.blog.modules.content.service.impl;

import com.dandandog.blog.modules.content.dao.BlogMetasDao;
import com.dandandog.blog.modules.content.entity.BlogMetas;
import com.dandandog.blog.modules.content.service.BlogMetasService;
import org.springframework.stereotype.Service;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;

/**
 * 元数据(BlogMetas)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-09 14:41:00
 */
@Service
public class BlogMetasServiceImpl extends BaseServiceImpl<BlogMetasDao, BlogMetas> implements BlogMetasService {

}
