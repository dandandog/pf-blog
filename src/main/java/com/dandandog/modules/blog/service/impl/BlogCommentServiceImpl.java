package com.dandandog.modules.blog.service.impl;

import com.dandandog.modules.blog.dao.BlogCommentDao;
import com.dandandog.modules.blog.entity.BlogComment;
import com.dandandog.modules.blog.service.BlogCommentService;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 评论(BlogComments)表服务实现类
 *
 * @author Johnny
 * @since 2021-09-09 14:40:56
 */
@Service
public class BlogCommentServiceImpl extends BaseServiceImpl<BlogCommentDao, BlogComment> implements BlogCommentService {

}
