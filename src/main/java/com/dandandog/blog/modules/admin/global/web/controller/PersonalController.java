package com.dandandog.blog.modules.admin.global.web.controller;

import com.dandandog.blog.modules.admin.auth.entity.AuthUser;
import com.dandandog.blog.modules.admin.auth.service.AuthUserService;
import com.dandandog.blog.modules.admin.global.web.faces.vo.UserInfoVo;
import com.dandandog.framework.common.utils.SecurityUtil;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.mapstruct.MapperUtil;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/26 11:34
 */

@Controller("/admin/global/personal.faces")
public class PersonalController extends FacesController {


    @Resource
    private AuthUserService userService;

    @Override
    public void onEntry() {
        AuthUser user = getCurrUser().orElse(null);
        UserInfoVo userVo = MapperUtil.map(user, UserInfoVo.class);
        putViewScope("user", userVo);
        putViewScope("avatar", null);
    }

    public void save() {
        UserInfoVo vo = getViewScope("user");
    }

    public void edit() {
        putViewScope("croppedImage", null);
        putViewScope("originalImageFile", null);
        putViewScope("originalImage", null);
    }


    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        InputStream inputStream = file.getInputStream();
        StreamedContent content = DefaultStreamedContent.builder()
                .contentType(file.getContentType())
                .stream(() -> inputStream)
                .build();
        putViewScope("originalImageFile", file);
        putViewScope("originalImage", content);
    }


    public StreamedContent getImage() throws IOException {
        UploadedFile file = getViewScope("originalImageFile");
        InputStream inputStream = file.getInputStream();
        return DefaultStreamedContent.builder()
                .contentType(file.getContentType())
                .stream(() -> inputStream)
                .build();
    }


    public void crop() {
        CroppedImage croppedImage = getViewScope("croppedImage");
        UploadedFile file = getViewScope("originalImageFile");
        byte[] bytes = croppedImage.getBytes();
        DefaultStreamedContent content = DefaultStreamedContent.builder()
                .contentType(file.getContentType())
                .stream(() -> new ByteArrayInputStream(bytes))
                .build();
        putViewScope("croppedImage", content);
    }


    private Optional<AuthUser> getCurrUser() {
        String uniqueId = SecurityUtil.getUniqueId();
        return userService.findByUsername(uniqueId);
    }

}
