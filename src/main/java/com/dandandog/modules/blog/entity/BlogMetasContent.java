package com.dandandog.modules.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 元数据关联内容 (BlogMetasContents)表实体类
 *
 * @author Johnny
 * @since 2021-09-09 14:41:01
 */
@Getter
@Setter
@TableName("blog_metas_contents")
public class BlogMetasContent extends BaseEntity {

    private String contentId;

    private String metaId;

    public BlogMetasContent() {

    }

    public BlogMetasContent(String contentId, String metaId) {
        this.contentId = contentId;
        this.metaId = metaId;
    }
}
