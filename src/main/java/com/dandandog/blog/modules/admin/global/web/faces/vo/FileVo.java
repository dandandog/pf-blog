package com.dandandog.blog.modules.admin.global.web.faces.vo;

import lombok.Data;
import org.primefaces.model.file.UploadedFile;

import java.io.Serializable;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/30 12:44
 */

@Data
public class FileVo implements Serializable {

    private UploadedFile file;

    public FileVo(UploadedFile file) {
        this.file = file;
    }
}
