package com.dandandog.blog.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.dandandog.blog.modules.system.dao.AuthRoleDao;
import com.dandandog.blog.modules.system.entity.AuthResource;
import com.dandandog.blog.modules.system.entity.AuthRole;
import com.dandandog.blog.modules.system.entity.AuthRoleResource;
import com.dandandog.blog.modules.system.service.AuthRoleResourceService;
import com.dandandog.blog.modules.system.service.AuthRoleService;
import com.dandandog.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统角色表(AuthRole)表服务实现类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Service
public class AuthRoleServiceImpl extends BaseServiceImpl<AuthRoleDao, AuthRole> implements AuthRoleService {

    @Resource
    private AuthRoleResourceService roleResourceService;


    @Override
    @Transactional
    public void saveOrUpdate(AuthRole role, List<AuthResource> resources) {
        if (saveOrUpdate(role)) {
            roleResourceService.remove(new LambdaQueryWrapper<AuthRoleResource>().eq(AuthRoleResource::getRoleId, role.getId()));
            List<AuthRoleResource> roleResources = CollUtil.emptyIfNull(resources).stream()
                    // 有权限的才保存到数据库中，主要是解决在 tree 上显示的问题
                    .filter(resource -> StringUtils.isNotBlank(resource.getPerms()))
                    .map(resource -> new AuthRoleResource(role.getId(), role.getCode(), resource.getId()))
                    .collect(Collectors.toList());
            if (roleResources.size() != 0) {
                roleResourceService.saveBatch(roleResources);
            }
        }
    }

    @Override
    public List<AuthRole> findByUser(String userId) {
        return baseMapper.findByUser(userId);
    }
}