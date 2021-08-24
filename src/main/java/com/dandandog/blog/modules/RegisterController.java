package com.dandandog.blog.modules;

import cn.hutool.core.util.RandomUtil;
import com.dandandog.blog.modules.system.entity.AuthUser;
import com.dandandog.blog.modules.system.entity.enums.UserState;
import com.dandandog.blog.modules.system.entity.enums.UserType;
import com.dandandog.blog.modules.system.service.AuthUserService;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageSeverity;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller("/register.faces")
public class RegisterController extends FacesController {

    @Resource
    private AuthUserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void onEntry() {
        int count = userService.count();
        UserType type = count == 0 ? UserType.ADMIN : UserType.USER;
        putViewScope("user", new AuthUser(type));
    }


    @MessageRequired(type = MessageType.OPERATION, severity = MessageSeverity.ERROR)
    public void save() {
        AuthUser user = getViewScope("user");
        AuthUser authUser = userService.lambdaQuery().eq(AuthUser::getUsername, user.getUsername()).one();
        if (authUser != null) {
            errorMessages("framework", "existUsername", authUser.getUsername());
        } else {
            user.setSalt(RandomUtil.randomString(6));
            user.setPwdRestTime(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setNickname(user.getUsername());
            userService.save(user);
            redirectInternal("/login");
        }
    }


    public void onCancel() {
        onEntry();
    }

}
