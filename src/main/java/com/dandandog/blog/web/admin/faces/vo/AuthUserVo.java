package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.mapstruct.model.MapperUrl;
import com.dandandog.modules.auth.entity.enums.UserState;
import com.dandandog.modules.auth.entity.enums.UserType;
import com.dandandog.modules.blog.entity.enums.GenderType;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/27 11:30
 */
@Data
public class AuthUserVo implements IVo {

    private String id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private UserState state;

    private UserType type;

    private String remark;

    private LocalDateTime pwdRestTime;

    private LocalDateTime operatedTime;

    private List<String> roles = Lists.newArrayList();

    private String nickname;

    private String email;

    private String phone;

    private GenderType gender;

    private MapperUrl avatarUrl;

    private String motto;

    private String homepage;

    private String address;

}
