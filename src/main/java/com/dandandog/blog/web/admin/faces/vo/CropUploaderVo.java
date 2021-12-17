package com.dandandog.blog.modules.admin.global.web.faces.vo;

import lombok.Data;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/30 12:44
 */

@Data
public class CropUploaderVo implements Serializable {

    private CroppedImage croppedImage;

    private byte[] content;

    private String contentType;

    private String fileName;

    private long size;

    private boolean exist;

    private String userId;


    public CropUploaderVo(String userId) {
        this.userId = userId;
    }

    public void setFile(UploadedFile file) {
        this.exist = Optional.ofNullable(file).map(uploadedFile -> {
            this.content = uploadedFile.getContent();
            this.contentType = uploadedFile.getContentType();
            this.fileName = uploadedFile.getFileName();
            this.size = uploadedFile.getSize();
            return true;
        }).orElse(false);
    }

    public StreamedContent getImage() {
        return DefaultStreamedContent.builder()
                .contentType(this.contentType)
                .stream(() -> new ByteArrayInputStream(this.content))
                .build();
    }

    public StreamedContent getCropped() {
        return DefaultStreamedContent.builder()
                .contentType(this.contentType)
                .stream(() -> new ByteArrayInputStream(this.croppedImage.getBytes()))
                .build();
    }
}
