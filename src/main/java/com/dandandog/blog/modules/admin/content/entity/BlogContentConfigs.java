package com.dandandog.blog.modules.admin.content.entity;

import com.dandandog.framework.core.entity.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内容配置(BlogContentConfigs)实体类
 *
 * @author makejava
 * @since 2021-09-24 23:21:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogContentConfigs extends AuditableEntity {

    /**
     * uuid
     */
    private String id;
    /**
     * 内容id
     */
    private String contentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private String value;

}
