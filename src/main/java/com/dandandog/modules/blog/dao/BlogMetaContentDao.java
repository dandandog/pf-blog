package com.dandandog.modules.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.modules.blog.entity.BlogMeta;
import com.dandandog.modules.blog.entity.BlogMetasContent;
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
public interface BlogMetaContentDao extends BaseMapper<BlogMetasContent> {

    @Select("SELECT bm.* FROM blog_metas bm LEFT JOIN blog_metas_contents bmc ON bm.id = bmc.meta_id WHERE bmc.content_id = #{contentId}")
    List<BlogMeta> getMetasByContent(@Param(value = "contentId") String contentId);


}
