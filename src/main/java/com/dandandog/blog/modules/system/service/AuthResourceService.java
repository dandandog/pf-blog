package com.dandandog.blog.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dandandog.blog.modules.system.entity.AuthResource;
import org.primefaces.model.TreeNode;

import java.util.List;

/**
 * 系统资源表(AuthResource)表服务接口
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
public interface AuthResourceService extends IService<AuthResource> {

    TreeNode getRootTree(boolean isExpand, AuthResource... selected);

    TreeNode getRootTree(Wrapper<AuthResource> queryWrapper, boolean isExpand, AuthResource... selected);

    List<AuthResource> findByRole(String id);

}