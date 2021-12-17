package com.dandandog.modules.auth.service.impl;

import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import com.dandandog.modules.auth.dao.AuthResourceDao;
import com.dandandog.modules.auth.dao.AuthRoleDao;
import com.dandandog.modules.auth.entity.AuthResource;
import com.dandandog.modules.auth.service.AuthResourceService;
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

    @Override
    public List<AuthResource> findByRole(String id) {
        return baseMapper.findByRole(id);
    }

    @Override
    public List<AuthResource> findByUser(String id) {
        return baseMapper.findByUser(id);
    }
}