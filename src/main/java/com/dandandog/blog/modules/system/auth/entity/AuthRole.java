package com.dandandog.blog.modules.system.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.core.entity.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 系统角色表(SysRole)实体类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_role")
public class AuthRole extends AuditableEntity {
    private static final long serialVersionUID = 812277922582345463L;

    /**
     * 角色名称
     */
    private String name;
    /**
     * 唯一标识
     */
    private String code;
    /**
     * 角色级别
     */
    private Integer level;
    /**
     * 数据范围
     */
    private Integer dataScope;
    /**
     * 备注
     */
    private String remark;


}