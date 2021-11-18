package com.dandandog.blog.modules.admin.content.service.impl;

import com.dandandog.blog.modules.admin.content.dao.BlogCommentsDao;
import com.dandandog.blog.modules.admin.content.entity.BlogComments;
import com.dandandog.blog.modules.admin.content.service.BlogCommentsService;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 评论(BlogComments)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-09 14:40:56
 */
@Service
public class BlogCommentsServiceImpl extends BaseServiceImpl<BlogCommentsDao, BlogComments> implements BlogCommentsService {

}
