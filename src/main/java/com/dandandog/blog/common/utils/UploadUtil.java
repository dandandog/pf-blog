package com.dandandog.blog.common.utils;

import com.dandandog.framework.mapstruct.model.MapperUrl;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import com.dandandog.framework.oos.service.OosFileService;
import org.primefaces.model.file.UploadedFile;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/14 10:02
 */
@Component
@Configuration
public class UploadUtil {

    private static OosFileService oosFileService;

    private static QualifierUrl qualifierUrl;

    public UploadUtil(OosFileService oosFileService, QualifierUrl qualifierUrl) {
        UploadUtil.oosFileService = oosFileService;
        UploadUtil.qualifierUrl = qualifierUrl;
    }

    public static MapperUrl componentUpload(UploadedFile file) throws IOException {
        String path = oosFileService.putItem(file.getFileName(), file.getInputStream());
        return qualifierUrl.addDomain(path);
    }
}
