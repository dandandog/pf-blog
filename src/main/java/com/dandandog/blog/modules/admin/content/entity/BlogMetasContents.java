package com.dandandog.blog.modules.admin.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 元数据关联内容 (BlogMetasContents)表实体类
 *
 * @author Johnny
 * @since 2021-09-09 14:41:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_metas_contents")
public class BlogMetasContents extends BaseEntity {

    private String contentId;

    private String metaId;

    public BlogMetasContents() {

    }

    public BlogMetasContents(String contentId, String metaId) {
        this.contentId = contentId;
        this.metaId = metaId;
    }
}
