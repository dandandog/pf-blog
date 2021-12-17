package com.dandandog.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import com.dandandog.modules.auth.entity.enums.UserState;
import com.dandandog.modules.auth.entity.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * 系统用户表(SysUser)实体类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Getter
@Setter
@TableName("auth_user")
public class AuthUser extends AuditableEntity {

    public AuthUser(UserType type) {
        this.type = type;
    }

    public AuthUser() {
    }

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 类型
     */
    private UserType type;

    /**
     * 状态（0：正常；1： 冻结；2：未激活)
     */
    private UserState state = UserState.NORMAL;

    /**
     * 备注
     */
    private String remark;

    /**
     * 密码上次更新时间
     */
    private LocalDateTime pwdRestTime;

    /**
     * 有效时间
     */
    private LocalDateTime expiredTime;

}