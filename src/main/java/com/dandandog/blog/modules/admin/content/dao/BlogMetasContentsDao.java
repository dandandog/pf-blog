package com.dandandog.blog.modules.admin.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.admin.content.entity.BlogMetas;
import com.dandandog.blog.modules.admin.content.entity.BlogMetasContents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 元数据关联内容 (BlogMetasContents)表数据库访问层
 *
 * @author Johnny
 * @since 2021-09-09 14:41:01
 */
@Mapper
public interface BlogMetasContentsDao extends BaseMapper<BlogMetasContents> {

    @Select("SELECT bm.* FROM blog_metas bm LEFT JOIN blog_metas_contents bmc ON bm.id = bmc.meta_id WHERE bmc.content_id = #{contentId}")
    List<BlogMetas> getMetasByContent(@Param(value = "contentId") String contentId);


}
