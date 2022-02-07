package com.dandandog.blog.web.admin.faces.vo;

import com.dandandog.framework.common.model.IVo;
import com.dandandog.modules.auth.entity.AuthRole;
import com.dandandog.modules.auth.entity.enums.UserState;
import com.dandandog.modules.auth.entity.enums.UserType;
import com.dandandog.modules.blog.entity.enums.GenderType;
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

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    private UserState state;

    private UserType type;

    private String remark;

    private LocalDateTime pwdRestTime;

    private LocalDateTime operatedTime;

    private DualListModel<AuthRole> roles = new DualListModel<>();

    private String nickname;

    private String email;

    private String phone;

    private GenderType gender;

    private String avatarUrl;

    private String motto;

    private String homepage;

    private String address;

}
