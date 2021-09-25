package com.dandandog.blog.modules.admin.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.admin.content.entity.BlogContentConfigs;
import com.dandandog.blog.modules.admin.content.entity.BlogContents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 内容配置(BlogContentConfigs)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-24 23:21:46
 */
@Mapper
public interface BlogContentConfigsDao extends BaseMapper<BlogContentConfigs> {


}

