package com.dandandog.blog.modules.admin.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserGender;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserStatus;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserType;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


/**
 * 系统用户表(SysUser)实体类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_user")
public class AuthUser extends AuditableEntity {
    private static final long serialVersionUID = -32722285353331566L;


    public AuthUser(UserType type) {
        this.type = type;
    }

    public AuthUser() {
    }

    /**
     * 昵称
     */
    private String nickname;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 类型
     */
    private UserType type;
    /**
     * 盐
     */
    private String salt;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 状态（0：正常；1： 冻结；2：未激活)
     */
    private UserStatus status = UserStatus.NORMAL;
    /**
     * 性别（0：男；1： 女；2：未知)
     */
    private UserGender gender = UserGender.UNKNOWN;
    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 座右铭
     */
    private String motto;

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