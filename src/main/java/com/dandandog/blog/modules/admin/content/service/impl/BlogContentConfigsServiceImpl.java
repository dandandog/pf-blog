package com.dandandog.blog.modules.admin.content.service.impl;

import com.dandandog.blog.modules.admin.content.dao.BlogContentConfigsDao;
import com.dandandog.blog.modules.admin.content.entity.BlogContentConfigs;
import com.dandandog.blog.modules.admin.content.service.BlogContentConfigsService;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 内容配置(BlogContentConfigs)表服务实现类
 *
 * @author makejava
 * @since 2021-09-24 23:21:46
 */
@Service
public class BlogContentConfigsServiceImpl extends BaseServiceImpl<BlogContentConfigsDao, BlogContentConfigs> implements BlogContentConfigsService {

}
