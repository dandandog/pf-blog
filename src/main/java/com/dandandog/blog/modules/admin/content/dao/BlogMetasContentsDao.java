package com.dandandog.blog.modules.admin.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.admin.content.entity.BlogMetasContents;
import org.apache.ibatis.annotations.Mapper;

/**
 * 元数据关联内容 (BlogMetasContents)表数据库访问层
 *
 * @author Johnny
 * @since 2021-09-09 14:41:01
 */
@Mapper
public interface BlogMetasContentsDao extends BaseMapper<BlogMetasContents> {

}
