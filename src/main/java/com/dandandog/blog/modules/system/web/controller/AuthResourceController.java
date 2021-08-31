package com.dandandog.blog.modules.system.web.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dandandog.blog.modules.system.entity.AuthResource;
import com.dandandog.blog.modules.system.entity.enums.ResourceTarget;
import com.dandandog.blog.modules.system.entity.enums.ResourceType;
import com.dandandog.blog.modules.system.service.AuthResourceService;
import com.dandandog.blog.modules.system.web.facet.vo.AuthResourceVo;
import com.dandandog.framework.core.entity.BaseEntity;
import com.dandandog.framework.faces.annotation.MessageRequired;
import com.dandandog.framework.faces.annotation.MessageType;
import com.dandandog.framework.faces.controller.FacesController;
import com.dandandog.framework.faces.exception.MessageResolvableException;
import com.dandandog.framework.mapstruct.MapperUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 系统资源(SysResource)表控制层
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Slf4j
@Controller("/system/auth/resource.faces")
public class AuthResourceController extends FacesController {

    /**
     * 服务对象
     */
    @Resource
    private AuthResourceService resourceService;

    @Override
    public void onEntry() {
        putViewScope("root", resourceService.getRootTree(true));

        putViewScope("vo", new AuthResourceVo());
        putViewScope("sinSelected", null);
        putViewScope("mulSelected", new TreeNode[0]);

        putViewScope("types", ResourceType.values());
        putViewScope("targets", ResourceTarget.values());
    }

    public void add() {
        putViewScope("icons", iconList());
        putResourceView(new AuthResourceVo());
    }

    @MessageRequired(type = MessageType.OPERATION)
    public void edit() {
        AuthResource selected = getViewScope("sinSelected");
        AuthResource target = Optional.ofNullable(resourceService.getById(selected.getId()))
                .orElseThrow(() -> new MessageResolvableException("error", "dataNotFound"));
        AuthResourceVo vo = MapperUtil.map(target, AuthResourceVo.class);
        putResourceView(vo);
    }

    @MessageRequired(type = MessageType.SAVE)
    public void save() {
        AuthResourceVo vo = getViewScope("vo");
        AuthResource entity = MapperUtil.map(vo, AuthResource.class);
        resourceService.saveOrUpdate(entity);
        onEntry();
    }

    @MessageRequired(type = MessageType.DELETE)
    public void delete() {
        AuthResource selected = getViewScope("sinSelected");
        TreeNode[] mulSelected = getViewScope("mulSelected");

        List<AuthResource> selectedList = Optional.ofNullable(mulSelected).map(treeNodes ->
                Arrays.stream(mulSelected)
                        .map(TreeNode::getData)
                        .map(o -> ((AuthResource) o))
                        .collect(Collectors.toList())).orElse(Lists.newArrayList());

        List<String> idList = CollUtil.defaultIfEmpty(selectedList, Lists.newArrayList(selected))
                .stream().map(BaseEntity::getId).collect(Collectors.toList());
        resourceService.removeByIds(idList);
        onEntry();
    }

    @MessageRequired(type = MessageType.OPERATION)
    public void onChangeStatus(AuthResource resource) {
        resourceService.updateById(resource);
        onEntry();
    }

    private void putResourceView(AuthResourceVo vo) {
        Wrapper<AuthResource> wrapper = new LambdaQueryWrapper<AuthResource>().ne(AuthResource::getType, ResourceType.BUTTON).orderByAsc(AuthResource::getSeq);
        putViewScope("vo", vo);
        putViewScope("rootTree", resourceService.getRootTree(wrapper, false, (AuthResource) vo.getParent().getData()));
    }

    public class Icon {

        private String name;
        private int key;

        public Icon(String name, int key) {
            this.name = name;
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }
    }

    public List<Icon> iconList() {
        List<Icon> icons = new ArrayList<>();
        try {
            String url = "selection.json";
            JSONObject json = readJsonFromUrl(url);
            JSONArray iconsArray = json.getJSONArray("icons");
            for (int i = 0; i < iconsArray.size(); i++) {
                JSONObject properties = iconsArray.getJSONObject(i).getJSONObject("properties");
                icons.add(new Icon(properties.getStr("name"), properties.getInt("code")));
            }
        } catch (IOException | JSONException ignored) {
        }
        return icons;
    }

    public JSONObject readJsonFromUrl(String url) throws IOException {
        ClassPathResource resource = new ClassPathResource(url);
        try (InputStream is = resource.getStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}