package com.dandandog.blog.modules.admin.config.service.impl;

import com.dandandog.blog.modules.admin.config.dao.BlogConfigsDao;
import com.dandandog.blog.modules.admin.config.entity.BlogConfigs;
import com.dandandog.blog.modules.admin.config.service.BlogConfigsService;
import org.springframework.stereotype.Service;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;

/**
 * 配置(BlogConfigs)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-22 09:47:29
 */
@Service
public class BlogConfigsServiceImpl extends BaseServiceImpl<BlogConfigsDao, BlogConfigs> implements BlogConfigsService {

}
