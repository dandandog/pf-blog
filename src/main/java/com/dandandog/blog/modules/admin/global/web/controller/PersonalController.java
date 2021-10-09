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
        CropUploaderVo cropUploaderVo = getSessionScope("cropUploader");
        personalFaces.saveOrUpdate(vo, cropUploaderVo);
        onEntry();
    }

    public void initCropUploader() {
        putSessionScope("cropUploader", null);
    }

    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        putSessionScope("cropUploader", new CropUploaderVo(file));
    }

}
