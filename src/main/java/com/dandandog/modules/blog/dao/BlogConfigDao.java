package com.dandandog.modules.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.modules.blog.entity.BlogConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 配置(BlogConfigs)表数据库访问层
 *
 * @author Johnny
 * @since 2021-09-22 09:47:27
 */
@Mapper
public interface BlogConfigDao extends BaseMapper<BlogConfig> {

}
