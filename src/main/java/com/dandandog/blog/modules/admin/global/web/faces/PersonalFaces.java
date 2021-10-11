package com.dandandog.blog.modules.admin.global.web.faces;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.dandandog.blog.modules.admin.auth.entity.AuthUser;
import com.dandandog.blog.modules.admin.auth.service.AuthUserService;
import com.dandandog.blog.modules.admin.auth.web.faces.vo.AuthUserVo;
import com.dandandog.blog.modules.admin.global.web.faces.vo.CropUploaderVo;
import com.dandandog.blog.security.service.AuthorizedService;
import com.dandandog.framework.common.utils.SecurityUtil;
import com.dandandog.framework.core.annotation.Facet;
import com.dandandog.framework.core.entity.BaseEntity;
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
    public void saveOrUpdate(AuthUserVo vo) {
        AuthUser user = MapperUtil.map(vo, AuthUser.class);
        userService.saveOrUpdate(user);
        authorizedService.reloadUserRole();
    }

    @Transactional
    public void updateAvatar(CropUploaderVo cropUploaderVo) {
        if (ObjectUtil.isNotNull(cropUploaderVo) && cropUploaderVo.isExist()) {
            oosFileService.removeItem(cropUploaderVo.getFileName());
            String url = oosFileService.putItem(cropUploaderVo.getFileName(), cropUploaderVo.getCropped().getStream());
            userService.lambdaUpdate().set(AuthUser::getAvatarUrl, url).eq(BaseEntity::getId, cropUploaderVo.getUserId()).update();
            authorizedService.reloadUserRole();
        }
    }

    public void updatePwd(AuthUserVo vo) {
        AuthUser user = MapperUtil.map(vo, AuthUser.class);
        if (StrUtil.isNotEmpty(user.getPassword())) {
            String salt = RandomUtil.randomString(6);
            userService.lambdaUpdate()
                    .set(AuthUser::getSalt, salt)
                    .set(AuthUser::getPassword, passwordEncoder.encode(user.getPassword() + salt))
                    .set(AuthUser::getPwdRestTime, LocalDateTime.now()).eq(BaseEntity::getId, vo.getId()).update();
        }
    }


    public AuthUserVo currUser() {
        String uniqueId = SecurityUtil.getUniqueId();
        AuthUser user = userService.findByUsername(uniqueId).orElseThrow(() -> new FaceletException(""));
        return MapperUtil.map(user, AuthUserVo.class);
    }


}
