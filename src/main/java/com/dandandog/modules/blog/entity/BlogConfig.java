package com.dandandog.modules.blog.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/27 12:36
 */
@Getter
@Setter
@TableName("blog_configs")
public class BlogConfig extends AuditableEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;


    /**
     * 内容id
     */
    private String contentId;


    @TableField(exist = false)
    private List<String> values;


    public BlogConfig(String name, String value, String contentId) {
        this.name = name;
        this.value = value;
        this.contentId = contentId;
    }

    public BlogConfig(String name, String value) {
        this.name = name;
        this.value = value;
        this.contentId = null;
    }

    public BlogConfig() {

    }


    public List<String> getValues() {
        return StrUtil.split(value, StrUtil.C_COMMA);
    }

    public void setValues(List<String> values) {
        this.value = String.join(StrUtil.COMMA, values);
    }


}
