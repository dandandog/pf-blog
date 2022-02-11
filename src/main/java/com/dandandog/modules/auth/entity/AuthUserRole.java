package com.dandandog.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * 系统用户角色关系表(SysUserRole)实体类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Getter
@Setter
@AllArgsConstructor
@TableName("auth_user_role")
public class AuthUserRole extends BaseEntity {
    /**
     * 用户uuid
     */
    private String userId;
    /**
     * 角色uuid
     */
    private String roleId;
    /**
     * 角色唯一标识
     */
    private String roleCode;


}