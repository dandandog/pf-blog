package com.dandandog.blog.modules.admin.config.entity;

import java.time.LocalDateTime;

import com.dandandog.framework.core.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.core.entity.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置(BlogConfigs)表实体类
 *
 * @author Johnny
 * @since 2021-09-22 09:47:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("blog_configs")
public class BlogConfigs extends AuditableEntity {


    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;


}
