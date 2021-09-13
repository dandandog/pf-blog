package com.dandandog.blog.modules.system.auth.service.impl;

import com.dandandog.blog.modules.system.auth.dao.AuthRoleDao;
import com.dandandog.blog.modules.system.auth.entity.AuthRole;
import com.dandandog.blog.modules.system.auth.service.AuthRoleService;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统角色表(AuthRole)表服务实现类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Service
public class AuthRoleServiceImpl extends BaseServiceImpl<AuthRoleDao, AuthRole> implements AuthRoleService {

    @Override
    public List<AuthRole> findByUser(String userId) {
        return baseMapper.findByUser(userId);
    }
}