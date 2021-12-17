package com.dandandog.modules.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.modules.blog.entity.BlogMeta;
import org.apache.ibatis.annotations.Mapper;

/**
 * 元数据(BlogMetas)表数据库访问层
 *
 * @author Johnny
 * @since 2021-09-09 14:41:00
 */
@Mapper
public interface BlogMetaDao extends BaseMapper<BlogMeta> {

}
