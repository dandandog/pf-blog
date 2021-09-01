package com.dandandog.blog.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dandandog.blog.common.model.TreeDataModel;
import com.dandandog.blog.modules.system.dao.AuthResourceDao;
import com.dandandog.blog.modules.system.entity.AuthResource;
import com.dandandog.blog.modules.system.service.AuthResourceService;
import com.dandandog.framework.common.model.ITree;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import com.google.common.collect.Multimap;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 系统资源表(AuthResource)表服务实现类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Service
public class AuthResourceServiceImpl extends BaseServiceImpl<AuthResourceDao, AuthResource> implements AuthResourceService {

    @Override
    public List<AuthResource> findByRole(String id) {
        return baseMapper.findByRole(id);
    }
}