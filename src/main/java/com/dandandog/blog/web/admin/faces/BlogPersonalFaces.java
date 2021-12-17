package com.dandandog.blog.web.admin.faces;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.dandandog.blog.modules.admin.global.web.faces.vo.CropUploaderVo;
import com.dandandog.blog.web.admin.faces.vo.AuthUserVo;
import com.dandandog.common.config.security.service.AuthorizedService;
import com.dandandog.framework.common.utils.SecurityUtil;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import com.dandandog.framework.oss.service.OssFileService;
import com.dandandog.modules.auth.entity.AuthUser;
import com.dandandog.modules.auth.service.AuthUserService;
import com.dandandog.modules.blog.entity.BlogPersonal;
import com.dandandog.modules.blog.service.BlogPersonalService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.faces.view.facelets.FaceletException;
import java.time.LocalDateTime;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/10/9 15:33
 */
@Faces
public class BlogPersonalFaces {

    @Resource
    private BlogPersonalService personalService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private OssFileService ossFileService;

    @Resource
    private AuthorizedService authorizedService;

    @Resource
    private AuthUserService userService;

    @Transactional
    public void saveOrUpdate(AuthUserVo vo) {
        AuthUser user = MapperUtil.map(vo, AuthUser.class);
        userService.saveOrUpdate(user);
        authorizedService.reloadUserRole();
    }

    @Transactional
    public void updateAvatar(CropUploaderVo cropUploaderVo) {
        if (ObjectUtil.isNotNull(cropUploaderVo) && cropUploaderVo.isExist()) {
            ossFileService.removeItem(cropUploaderVo.getFileName());
            String url = ossFileService.putItem(cropUploaderVo.getFileName(), cropUploaderVo.getCropped().getStream());
            personalService.lambdaUpdate().set(BlogPersonal::getAvatarUrl, url).eq(BaseEntity::getId, cropUploaderVo.getUserId()).update();
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
