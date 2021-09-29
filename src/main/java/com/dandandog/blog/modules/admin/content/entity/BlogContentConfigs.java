package com.dandandog.blog.modules.admin.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.blog.modules.admin.BaseConfigs;
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
@TableName("blog_content_configs")
public class BlogContentConfigs extends BaseConfigs {

    /**
     * 内容id
     */
    private String contentId;


    public BlogContentConfigs() {
    }

    public BlogContentConfigs(String name, String value, String contentId) {
        super(name, value);
        this.contentId = contentId;
    }
}
