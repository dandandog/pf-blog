package com.dandandog.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * 系统角色资源关系表(SysRoleResource)实体类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */

@Getter
@Setter
@AllArgsConstructor
@TableName("auth_role_resource")
public class AuthRoleResource extends BaseEntity {

    /**
     * 角色uuid
     */
    private String roleId;

    /**
     * 菜单uuid
     */
    private String resId;


}