package com.dandandog.blog.common.view;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.dandandog.framework.mapstruct.model.MapperUrl;
import com.dandandog.framework.mapstruct.qualifier.QualifierUrl;
import com.dandandog.framework.oss.service.OssFileService;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.model.file.UploadedFileWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author JohnnyLiu
 */
@RestController("guestFileUpload")
public class GuestFileUpload {

    @Resource
    private OssFileService ossFileService;

    @Resource
    private QualifierUrl qualifierUrl;

    @Getter
    @Setter
    private UploadedFileWrapper value;

    private static final String REGEX = "[^0-9]";

    @GetMapping("/api/test")
    public String test() {
        return "hello world";
    }


    @PostMapping("/api/upload")
    public List<String> upload(@RequestParam("files") MultipartFile[] files) {
        if (ArrayUtil.isNotEmpty(files)) {
            List<String> urls = Arrays.stream(files).map(file -> {
                try {
                    String path = ossFileService.putItem(file.getOriginalFilename(), file.getInputStream());
                    return qualifierUrl.addDomain(path).toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }).filter(StrUtil::isNotEmpty).collect(Collectors.toList());
            return CollUtil.sort(urls, mapperUrlComparable());
        }
        return null;
    }

    public void upload(FileUploadEvent event) throws IOException {
        UploadedFile file = event.getFile();
        String path = ossFileService.putItem(file.getFileName(), file.getInputStream());
        MapperUrl value = qualifierUrl.addDomain(path);

        ValueExpression valueExpression = getValueExpression(event.getComponent(), "value");
        ELContext elContext = getELContext(event.getFacesContext());

        Class<?> clazz = valueExpression.getType(elContext);
        if (clazz.isAssignableFrom(List.class)) {
            List<MapperUrl> list = CollUtil.defaultIfEmpty((List) valueExpression.getValue(elContext), Lists.newArrayList());
            list.add(value);
            setValue(valueExpression, elContext, list);
        } else if (clazz.isAssignableFrom(MapperUrl.class)) {
            setValue(valueExpression, elContext, value);
        } else if (clazz.isAssignableFrom(String.class)) {
            setValue(valueExpression, elContext, value.toString());
        }
    }

    private Comparator<CharSequence> mapperUrlComparable() {
        return (o1, o2) -> {
            if (o1 == null || o2 == null) {
                return 0;
            }
            String no1 = ReUtil.replaceAll(o1, REGEX, "");
            String no2 = ReUtil.replaceAll(o2, REGEX, "");
            return ObjectUtil.compare(Integer.valueOf(no1), Integer.valueOf(no2));
        };
    }


    public void remove(ActionEvent event) {
        ValueExpression valueExpression = getValueExpression(event.getComponent(), "value");
        ELContext elContext = getELContext(event.getFacesContext());
        Class<?> clazz = valueExpression.getType(elContext);
        if (clazz.isAssignableFrom(List.class)) {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String index = params.get("index");
            List<MapperUrl> list = (List) valueExpression.getValue(elContext);
            int integer = Integer.valueOf(index);
            list.remove(integer);
            setValue(valueExpression, elContext, list);
        } else if (clazz.isAssignableFrom(MapperUrl.class)) {
            setValue(valueExpression, elContext, null);
        }
    }


    public void sorting(ActionEvent event) {
        ValueExpression valueExpression = getValueExpression(event.getComponent(), "value");
        ELContext elContext = getELContext(event.getFacesContext());
        Class<?> clazz = valueExpression.getType(elContext);
        if (clazz.isAssignableFrom(List.class)) {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            String index = params.get("index");
            List<MapperUrl> list = (List) valueExpression.getValue(elContext);
            int integer = Integer.valueOf(index);
            if (integer != 0) {
                MapperUrl mapperUrl = list.remove(integer);
                list.add(integer - 1, mapperUrl);
            }
            setValue(valueExpression, elContext, list);
        }
    }

    private ValueExpression getValueExpression(UIComponent component, String key) {
        return component.getNamingContainer().getValueExpression(key);
    }

    private ELContext getELContext(FacesContext facesContext) {
        return facesContext.getELContext();
    }

    private void setValue(ValueExpression valueExpression, ELContext elContext, Object value) {
        valueExpression.setValue(elContext, value);
    }


}
