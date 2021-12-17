package com.dandandog.modules.blog.service.impl;

import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import com.dandandog.modules.blog.dao.BlogConfigDao;
import com.dandandog.modules.blog.entity.BlogConfig;
import com.dandandog.modules.blog.service.BlogConfigService;
import org.springframework.stereotype.Service;

/**
 * 配置(BlogConfigs)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-22 09:47:29
 */
@Service
public class BlogConfigServiceImpl extends BaseServiceImpl<BlogConfigDao, BlogConfig> implements BlogConfigService {

}
