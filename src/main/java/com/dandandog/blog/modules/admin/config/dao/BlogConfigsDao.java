package com.dandandog.blog.modules.admin.config.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.admin.config.entity.BlogConfigs;
import org.apache.ibatis.annotations.Mapper;

/**
 * 配置(BlogConfigs)表数据库访问层
 *
 * @author Johnny
 * @since 2021-09-22 09:47:27
 */
@Mapper
public interface BlogConfigsDao extends BaseMapper<BlogConfigs> {

}
