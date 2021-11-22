package com.dandandog.blog.common.utils;

import com.dandandog.framework.mapstruct.model.MapperUrl;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import com.dandandog.framework.oss.service.OssFileService;
import org.primefaces.model.file.UploadedFile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/14 10:02
 */
@Component
public class UploadUtil {

    private static OssFileService ossFileService;

    private static QualifierUrl qualifierUrl;

    public UploadUtil(OssFileService ossFileService, QualifierUrl qualifierUrl) {
        UploadUtil.ossFileService = ossFileService;
        UploadUtil.qualifierUrl = qualifierUrl;
    }

    public static MapperUrl componentUpload(UploadedFile file) throws IOException {
        String path = ossFileService.putItem(file.getFileName(), file.getInputStream());
        return qualifierUrl.addDomain(path);
    }
}
