package com.dandandog.modules.blog.service.impl;

import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import com.dandandog.modules.blog.dao.BlogPersonalDao;
import com.dandandog.modules.blog.entity.BlogPersonal;
import com.dandandog.modules.blog.service.BlogPersonalService;
import org.springframework.stereotype.Service;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/12/16 9:27
 */
@Service
public class BlogPersonalServiceImpl extends BaseServiceImpl<BlogPersonalDao, BlogPersonal> implements BlogPersonalService {
}
