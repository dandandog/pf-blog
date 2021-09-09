package com.dandandog.blog.modules.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.blog.entity.BlogMetas;
import org.apache.ibatis.annotations.Mapper;

/**
 * 元数据(BlogMetas)表数据库访问层
 *
 * @author Johnny
 * @since 2021-09-09 14:41:00
 */
@Mapper
public interface BlogMetasDao extends BaseMapper<BlogMetas> {

}
