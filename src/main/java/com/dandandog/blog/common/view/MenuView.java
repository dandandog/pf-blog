package com.dandandog.blog.common.view;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dandandog.blog.common.model.TreeDataModel;
import com.dandandog.blog.modules.system.entity.AuthResource;
import com.dandandog.blog.modules.system.entity.AuthUser;
import com.dandandog.blog.modules.system.service.AuthResourceService;
import com.dandandog.blog.modules.system.service.AuthRoleService;
import com.dandandog.blog.modules.system.service.AuthUserService;
import com.dandandog.framework.common.utils.SecurityUtil;
import com.google.common.collect.Multimap;
import org.primefaces.model.menu.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/1 16:19
 */
@Component("menuView")
public class MenuView {

    @Resource
    private AuthUserService userService;

    @Resource
    private AuthResourceService resourceService;

    @Resource
    private AuthRoleService roleService;

    public MenuModel getData() {
        String uniqueId = SecurityUtil.getUniqueId();
        Optional<AuthUser> OptUser = userService.findByUsername(uniqueId);
        AuthUser user = OptUser.orElseThrow(() -> new RuntimeException(""));
        MenuModel model = new DefaultMenuModel();

        TreeDataModel<AuthResource> treeDataModel = new TreeDataModel<>(AuthResource.class);

        Multimap<String, AuthResource> multimap = treeDataModel.getValueById(Wrappers.emptyWrapper());

        Collection<AuthResource> authResources = multimap.removeAll(null);
        for (AuthResource authResource : authResources) {
            DefaultSubMenu firstSubmenu = DefaultSubMenu.builder()
                    .id(authResource.getId())
                    .icon(authResource.getIcon())
                    .label(authResource.getTitle())
                    .build();
            list(firstSubmenu, multimap);
            model.getElements().add(firstSubmenu);
        }

        return model;
    }

    private void list(DefaultSubMenu subMenu, Multimap<String, AuthResource> multimap) {
        Collection<AuthResource> authResources = multimap.removeAll(subMenu.getId());
        for (AuthResource authResource : authResources) {
            MenuElement item;
            if (authResource.isLeaf()) {
                item = DefaultMenuItem.builder()
                        .id(authResource.getId())
                        .value(authResource.getTitle())
                        .icon(authResource.getIcon())
                        .outcome(authResource.getPath())
                        .build();
            } else {
                item = DefaultSubMenu.builder()
                        .id(authResource.getId())
                        .label(authResource.getTitle())
                        .icon(authResource.getIcon())
                        .build();
                list((DefaultSubMenu) item, multimap);
            }
            subMenu.getElements().add(item);
        }
    }


}
