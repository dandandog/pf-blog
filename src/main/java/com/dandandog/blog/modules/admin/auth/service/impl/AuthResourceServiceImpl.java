package com.dandandog.blog.modules.admin.auth.service.impl;

import com.dandandog.blog.modules.admin.auth.dao.AuthResourceDao;
import com.dandandog.blog.modules.admin.auth.dao.AuthRoleDao;
import com.dandandog.blog.modules.admin.auth.entity.AuthResource;
import com.dandandog.blog.modules.admin.auth.service.AuthResourceService;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统资源表(AuthResource)表服务实现类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Service
public class AuthResourceServiceImpl extends BaseServiceImpl<AuthResourceDao, AuthResource> implements AuthResourceService {

    @Resource
    private AuthRoleDao roleDao;


    @Override
    public List<AuthResource> findByRole(String id) {
        return baseMapper.findByRole(id);
    }

    @Override
    public List<AuthResource> findByUser(String id) {
        return roleDao.findByUser(id).stream().flatMap(authRole ->
                findByRole(authRole.getId()).stream()).distinct().collect(Collectors.toList());
    }
}