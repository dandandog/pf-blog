package com.dandandog.blog.views;

import com.dandandog.common.utils.TreeUtil;
import com.dandandog.framework.common.utils.SecurityUtil;
import com.dandandog.modules.auth.entity.AuthResource;
import com.dandandog.modules.auth.entity.AuthUser;
import com.dandandog.modules.auth.entity.enums.UserType;
import com.dandandog.modules.auth.service.AuthResourceService;
import com.dandandog.modules.auth.service.AuthUserService;
import com.google.common.collect.Multimap;
import lombok.Getter;
import org.primefaces.model.menu.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
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


    public void initialize() {
        model = new DefaultMenuModel();
        AuthUser user = getCurrUser();
        List<AuthResource> resources = UserType.USER.equals(user.getType()) ?
                resourceService.findByUser(user.getId()) : resourceService.list();
        Multimap<String, AuthResource> multimap = TreeUtil.parentMap(resources);
        Collection<AuthResource> target = multimap.get(null);
        for (AuthResource authResource : target) {
            DefaultSubMenu firstSubmenu = createSubMenu(authResource);
            list(firstSubmenu, multimap);
            model.getElements().add(firstSubmenu);
        }
    }

    private AuthUser getCurrUser() {
        String uniqueId = SecurityUtil.getUniqueId();
        Optional<AuthUser> OptUser = userService.findByUsername(uniqueId);
        return OptUser.orElseThrow(() -> new RuntimeException(""));
    }

    private void list(DefaultSubMenu subMenu, Multimap<String, AuthResource> multimap) {
        Collection<AuthResource> authResources = multimap.removeAll(subMenu.getId());
        for (AuthResource resource : authResources) {
            MenuElement item = resource.isLeaf() ? createMenuItem(resource) : createSubMenu(resource);
            if (!resource.isLeaf()) {
                assert item instanceof DefaultSubMenu;
                list((DefaultSubMenu) item, multimap);
            }
            subMenu.getElements().add(item);
        }
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
