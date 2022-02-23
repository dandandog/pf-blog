package com.dandandog.blog.views;

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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Getter
    private MenuModel model;

    @Getter
    private final Map<String, List<String>> viewName = Maps.newHashMap();

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
            if (element instanceof DefaultSubMenu) {
                DefaultSubMenu menu = (DefaultSubMenu) element;
                params.add(menu.getLabel());
                buildMenu(multimap, params, menu, resource.getId());
                if (subMenu == null) {
                    target.add(element);
                }
            } else {
                DefaultMenuItem item = (DefaultMenuItem) element;
                params.add((String) item.getValue());
                viewName.put("/blog" + item.getOutcome() + ".faces", params);
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
