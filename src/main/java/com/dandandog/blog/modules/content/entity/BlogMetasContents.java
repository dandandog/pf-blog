package com.dandandog.blog.modules.content.entity;

import com.dandandog.framework.core.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
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
public class BlogMetasContents extends AbstractEntity {

    private String contentId;

    private String metaId;
}
