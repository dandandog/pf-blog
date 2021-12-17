package com.dandandog.modules.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.modules.blog.entity.BlogContent;
import org.apache.ibatis.annotations.Mapper;

/**
 * 内容(BlogContents)表数据库访问层
 *
 * @author Johnny
 * @since 2021-09-09 14:40:59
 */
@Mapper
public interface BlogContentDao extends BaseMapper<BlogContent> {

}
