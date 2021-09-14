package com.dandandog.blog.modules.admin.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.core.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 系统角色资源关系表(SysRoleResource)实体类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("auth_role_resource")
public class AuthRoleResource extends BaseEntity {
    private static final long serialVersionUID = -87971289520291131L;

    /**
     * 角色uuid
     */
    private String roleId;

    /**
     * 角色uuid
     */
    private String roleCode;

    /**
     * 菜单uuid
     */
    private String resId;


}