package com.dandandog.blog.modules.admin.auth.web.faces.vo;

import com.dandandog.blog.modules.admin.auth.entity.AuthRole;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserGender;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserStatus;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserType;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.framework.mapstruct.model.MapperUrl;
import lombok.Data;
import org.primefaces.model.DualListModel;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/27 11:30
 */
@Data
public class AuthUserVo implements IVo {

    private String id;

    private String nickname;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private String email;

    private String phone;

    private UserStatus status;

    private UserGender gender;

    private UserType type;

    private MapperUrl avatarUrl;

    private String remark;

    private LocalDateTime pwdRestTime;

    private LocalDateTime operatedTime;

    private DualListModel<AuthRole> roles = new DualListModel<>();

}
