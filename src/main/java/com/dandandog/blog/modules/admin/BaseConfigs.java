package com.dandandog.blog.modules.admin;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class BaseConfigs extends AuditableEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;


    @TableField(exist = false)
    private List<String> values;

    public List<String> getValues() {
        return StrUtil.split(value, StrUtil.C_COMMA);
    }

    public void setValues(List<String> values) {
        this.value = String.join(StrUtil.COMMA, values);
    }

    public BaseConfigs(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public BaseConfigs() {

    }

}
