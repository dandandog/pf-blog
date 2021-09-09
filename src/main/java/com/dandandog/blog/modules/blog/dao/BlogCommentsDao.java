package com.dandandog.blog.modules.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.blog.entity.BlogComments;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论(BlogComments)表数据库访问层
 *
 * @author Johnny
 * @since 2021-09-09 14:40:54
 */
@Mapper
public interface BlogCommentsDao extends BaseMapper<BlogComments> {

}
