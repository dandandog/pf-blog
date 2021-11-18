package com.dandandog.blog.modules.admin.content.service.impl;

import com.dandandog.blog.modules.admin.content.dao.BlogContentsDao;
import com.dandandog.blog.modules.admin.content.entity.BlogContents;
import com.dandandog.blog.modules.admin.content.service.BlogContentsService;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 内容(BlogContents)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-09 14:40:59
 */
@Service
public class BlogContentsServiceImpl extends BaseServiceImpl<BlogContentsDao, BlogContents> implements BlogContentsService {

}
