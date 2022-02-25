package com.dandandog.blog.views;

import com.dandandog.blog.web.admin.faces.AuthResourceFaces;
import com.dandandog.common.utils.TreeUtil;
import com.dandandog.framework.common.utils.SecurityUtil;
import com.dandandog.modules.auth.entity.AuthResource;
import com.dandandog.modules.auth.entity.AuthUser;
import com.dandandog.modules.auth.entity.enums.UserType;
import com.dandandog.modules.auth.service.AuthResourceService;
import com.dandandog.modules.auth.service.AuthUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import lombok.Getter;
import org.primefaces.model.menu.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/1 16:19
 */
@Component("menuView")
@SessionScope
public class MenuView {

    @Resource
    private AuthUserService userService;

    @Resource
    private AuthResourceService resourceService;

    @Resource
    private AuthResourceFaces resourceFaces;

    @Getter
    private MenuModel model;

    @Getter
    private final Map<String, Collection<String>> viewName = Maps.newHashMap();

    public void initialize() {
        model = new DefaultMenuModel();
        AuthUser user = getCurrUser();
        List<AuthResource> resources = UserType.USER.equals(user.getType()) ?
                resourceService.findByUser(user.getId()) : resourceService.list();
        Multimap<String, AuthResource> multimap = TreeUtil.parentMap(resources);
        List<MenuElement> menus = buildMenu(multimap, Lists.newArrayList(), null, null);
        model.getElements().addAll(menus);
    }

    public List<MenuElement> buildMenu(Multimap<String, AuthResource> multimap, List<String> params, DefaultSubMenu subMenu, String parentId) {
        Collection<AuthResource> authResources = multimap.removeAll(parentId);
        List<MenuElement> target = Lists.newArrayList();

        for (AuthResource resource : authResources) {
            MenuElement element = resource.isLeaf() ? createMenuItem(resource) : createSubMenu(resource);
            List<String> view = new ArrayList<>(params);
            if (element instanceof DefaultSubMenu) {
                if (subMenu == null) {
                    params = Lists.newArrayList();
                    target.add(element);
                }
                DefaultSubMenu menu = (DefaultSubMenu) element;
                view.add(menu.getLabel());
                buildMenu(multimap, view, menu, resource.getId());
            } else {
                DefaultMenuItem item = (DefaultMenuItem) element;
                view.add((String) item.getValue());
                viewName.put("/blog" + item.getOutcome() + ".faces", view);
            }
            if (subMenu != null) {
                subMenu.getElements().add(element);
            }
        }
        return target;
    }


    private AuthUser getCurrUser() {
        String uniqueId = SecurityUtil.getUniqueId();
        Optional<AuthUser> OptUser = userService.findByUsername(uniqueId);
        return OptUser.orElseThrow(() -> new RuntimeException(""));
    }

    private DefaultSubMenu createSubMenu(AuthResource resource) {
        return DefaultSubMenu.builder()
                .id(resource.getId())
                .label(resource.getTitle())
                .icon("pi pi-" + resource.getIcon())
                .build();
    }

    private DefaultMenuItem createMenuItem(AuthResource resource) {
        return DefaultMenuItem.builder()
                .id(resource.getId())
                .value(resource.getTitle())
                .icon("pi pi-" + resource.getIcon())
                .outcome(resource.getPath())
                .onclick("PrimeFaces.monitorDownload(start, stop);")
                .build();
    }

}
