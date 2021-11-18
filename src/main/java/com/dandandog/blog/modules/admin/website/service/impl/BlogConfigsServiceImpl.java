package com.dandandog.blog.modules.admin.website.service.impl;

import com.dandandog.blog.modules.admin.website.dao.BlogConfigsDao;
import com.dandandog.blog.modules.admin.website.entity.BlogConfigs;
import com.dandandog.blog.modules.admin.website.service.BlogConfigsService;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 配置(BlogConfigs)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-22 09:47:29
 */
@Service
public class BlogConfigsServiceImpl extends BaseServiceImpl<BlogConfigsDao, BlogConfigs> implements BlogConfigsService {

}
