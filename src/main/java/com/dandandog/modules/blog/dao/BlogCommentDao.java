package com.dandandog.modules.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.modules.blog.entity.BlogComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论(BlogComments)表数据库访问层
 *
 * @author Johnny
 * @since 2021-09-09 14:40:54
 */
@Mapper
public interface BlogCommentDao extends BaseMapper<BlogComment> {

}
