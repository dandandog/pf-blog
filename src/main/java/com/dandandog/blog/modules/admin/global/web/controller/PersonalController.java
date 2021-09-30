package com.dandandog.blog.modules.admin.global.web.controller;

import com.dandandog.blog.modules.admin.auth.entity.AuthUser;
import com.dandandog.blog.modules.admin.auth.service.AuthUserService;
import com.dandandog.blog.modules.admin.global.web.faces.vo.FileVo;
import com.dandandog.blog.modules.admin.global.web.faces.vo.UserInfoVo;
import com.dandandog.framework.common.utils.SecurityUtil;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import com.dandandog.framework.oos.service.OosFileService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/26 11:34
 */

@Controller("/admin/global/personal.faces")
public class PersonalController extends FacesController {

    @Resource
    private AuthUserService userService;

    @Resource
    private CropUploaderBean cropUploaderBean;

    @Resource
    private OosFileService oosFileService;

    @Resource
    private QualifierUrl qualifierUrl;

    @Override
    public void onEntry() {
        AuthUser user = getCurrUser().orElse(null);
        UserInfoVo userVo = MapperUtil.map(user, UserInfoVo.class);
        putViewScope("user", userVo);
        putViewScope("avatar", null);
    }

    public void save() {
        UploadedFile imgFile = cropUploaderBean.getOriginalImageFile();
        String url = oosFileService.putItem(imgFile.getFileName(), cropUploaderBean.getCropped().getStream());
        UserInfoVo vo = getViewScope("user");
        vo.setAvatarUrl(qualifierUrl.addDomain(url));
        AuthUser user = MapperUtil.map(vo, AuthUser.class);
        userService.saveOrUpdate(user);

    }

    public void edit() {
        cropUploaderBean.clear();
        putViewScope("croppedImage", null);
        putSessionScope("originalImageFile", null);
        putSessionScope("originalImage", null);
    }


    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        byte[] bytes = file.getContent();
        StreamedContent content = DefaultStreamedContent.builder()
                .contentType(file.getContentType())
                .stream(() -> new ByteArrayInputStream(bytes))
                .build();
        putSessionScope("originalImageFile", new FileVo(file));
        putSessionScope("originalImage", content);
    }


    public StreamedContent getImage() {
        UploadedFile file = getViewScope("originalImageFile");
        byte[] bytes = file.getContent();
        return DefaultStreamedContent.builder()
                .contentType(file.getContentType())
                .stream(() -> new ByteArrayInputStream(bytes))
                .build();
    }

    public StreamedContent getCropped() {
        UploadedFile file = getViewScope("originalImageFile");
        CroppedImage croppedImage = getViewScope("croppedImage");
        byte[] bytes = croppedImage.getBytes();
        return DefaultStreamedContent.builder()
                .contentType(file.getContentType())
                .stream(() -> new ByteArrayInputStream(bytes))
                .build();
    }


    public void crop() {
        CroppedImage croppedImage = getViewScope("croppedImage");
        FileVo vo = getViewScope("originalImageFile");
        byte[] bytes = croppedImage.getBytes();
        DefaultStreamedContent content = DefaultStreamedContent.builder()
                .contentType(vo.getFile().getContentType())
                .stream(() -> new ByteArrayInputStream(bytes))
                .build();
        putSessionScope("croppedImage", content);
    }


    private Optional<AuthUser> getCurrUser() {
        String uniqueId = SecurityUtil.getUniqueId();
        return userService.findByUsername(uniqueId);
    }

}
