package com.dandandog.blog.modules.blog.service.impl;

import com.dandandog.blog.modules.blog.dao.BlogContentsDao;
import com.dandandog.blog.modules.blog.entity.BlogContents;
import com.dandandog.blog.modules.blog.service.BlogContentsService;
import org.springframework.stereotype.Service;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;

/**
 * 内容(BlogContents)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-09 14:40:59
 */
@Service
public class BlogContentsServiceImpl extends BaseServiceImpl<BlogContentsDao, BlogContents> implements BlogContentsService {

}
