package com.dandandog.blog.modules;

import cn.hutool.core.util.RandomUtil;
import com.dandandog.blog.modules.admin.auth.entity.AuthUser;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserType;
import com.dandandog.blog.modules.admin.auth.service.AuthUserService;
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
        Optional<AuthUser> optUser = userService.findByUsername(user.getUsername());
        optUser.map(authUser -> {
            errorMessages("framework", "existUsername", authUser.getUsername());
            return authUser;
        }).orElseGet(() -> {
            user.setSalt(RandomUtil.randomString(6));
            user.setPassword(passwordEncoder.encode(user.getPassword() + user.getSalt()));
            user.setNickname(user.getUsername());
            user.setPwdRestTime(LocalDateTime.now());
            userService.save(user);
            redirectInternal("/login");
            return user;
        });
    }


    public void onCancel() {
        onEntry();
    }

}
