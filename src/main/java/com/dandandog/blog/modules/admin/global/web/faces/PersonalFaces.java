package com.dandandog.blog.modules.admin.global.web.faces;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.dandandog.blog.modules.admin.auth.entity.AuthUser;
import com.dandandog.blog.modules.admin.auth.service.AuthUserService;
import com.dandandog.blog.modules.admin.auth.web.faces.vo.AuthUserVo;
import com.dandandog.blog.modules.admin.global.web.faces.vo.CropUploaderVo;
import com.dandandog.blog.security.service.AuthorizedService;
import com.dandandog.framework.common.utils.SecurityUtil;
import com.dandandog.framework.core.annotation.Facet;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.dandandog.framework.oos.service.OosFileService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.faces.view.facelets.FaceletException;
import java.time.LocalDateTime;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/10/9 15:33
 */
@Facet
public class PersonalFaces {

    @Resource
    private AuthUserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private OosFileService oosFileService;

    @Resource
    private AuthorizedService authorizedService;

    @Transactional
    public void saveOrUpdate(AuthUserVo vo, CropUploaderVo cropUploaderVo) {
        AuthUser user = MapperUtil.map(vo, AuthUser.class);

        if (ObjectUtil.isNotNull(cropUploaderVo) && cropUploaderVo.isExist()) {
            String url = oosFileService.putItem(cropUploaderVo.getFileName(), cropUploaderVo.getCropped().getStream());
            user.setAvatarUrl(url);
        }

        if (user.getPassword() != null) {
            user.setSalt(RandomUtil.randomString(6));
            user.setPassword(passwordEncoder.encode(user.getPassword() + user.getSalt()));
            user.setPwdRestTime(LocalDateTime.now());
        }
        userService.saveOrUpdate(user);
        authorizedService.reloadUserRole();
    }

    public AuthUserVo currUser() {
        String uniqueId = SecurityUtil.getUniqueId();
        AuthUser user = userService.findByUsername(uniqueId).orElseThrow(() -> new FaceletException(""));
        return MapperUtil.map(user, AuthUserVo.class);
    }
}
