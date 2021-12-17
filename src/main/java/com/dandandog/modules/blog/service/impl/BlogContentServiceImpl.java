package com.dandandog.modules.blog.service.impl;

import com.dandandog.modules.blog.dao.BlogContentDao;
import com.dandandog.modules.blog.entity.BlogContent;
import com.dandandog.modules.blog.service.BlogContentService;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 内容(BlogContents)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-09 14:40:59
 */
@Service
public class BlogContentServiceImpl extends BaseServiceImpl<BlogContentDao, BlogContent> implements BlogContentService {

}
