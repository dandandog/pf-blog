package com.dandandog.blog.modules.admin.website.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.blog.modules.admin.BaseConfigs;
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
public class BlogConfigs extends BaseConfigs {

    public BlogConfigs() {
    }

    public BlogConfigs(String name, String value) {
        super(name, value);
    }
}
