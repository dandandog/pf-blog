package com.dandandog.blog.modules.admin.global.web.faces.vo;

import com.dandandog.blog.modules.admin.auth.entity.enums.UserGender;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserStatus;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserType;
import com.dandandog.framework.mapstruct.model.MapperUrl;
import com.dandandog.framework.mapstruct.model.MapperVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/26 14:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoVo extends MapperVo {

    private String nickname;

    private String username;

    private String password;

    private UserType type;

    private String salt;

    private String email;

    private String phone;

    private UserStatus status = UserStatus.NORMAL;

    private UserGender gender = UserGender.UNKNOWN;

    private MapperUrl avatarUrl;

    private String remark;

    private LocalDateTime pwdRestTime;


}
