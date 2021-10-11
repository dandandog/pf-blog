package com.dandandog.blog.modules.admin.global.web.controller;

import com.dandandog.blog.modules.admin.auth.web.faces.vo.AuthUserVo;
import com.dandandog.blog.modules.admin.global.web.faces.PersonalFaces;
import com.dandandog.blog.modules.admin.global.web.faces.vo.CropUploaderVo;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/26 11:34
 */

@Controller("/admin/global/personal.faces")
public class PersonalController extends FacesController {

    @Resource
    private PersonalFaces personalFaces;

    @Override
    public void onEntry() {
        AuthUserVo userVo = personalFaces.currUser();
        putViewScope("user", userVo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        AuthUserVo vo = getViewScope("user");
        personalFaces.saveOrUpdate(vo);
        onEntry();
    }

    @MessageRequired(type = MessageType.SAVE)
    public void handleUpdateAvatar() {
        CropUploaderVo cropUploaderVo = getSessionScope("cropUploader");
        personalFaces.updateAvatar(cropUploaderVo);
        onEntry();
    }

    @MessageRequired(type = MessageType.SAVE)
    public void handUpdatePwd() {
        AuthUserVo vo = getViewScope("user");
        personalFaces.updatePwd(vo);
        onEntry();
    }

    public void initCropUploader() {
        AuthUserVo vo = getViewScope("user");
        putSessionScope("cropUploader", new CropUploaderVo(vo.getId()));
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        CropUploaderVo cropUploader = getSessionScope("cropUploader");
        cropUploader.setFile(file);
    }
}
