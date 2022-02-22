package com.dandandog.blog.web.admin.faces.adapter;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dandandog.common.adapter.AbstractPageAdapter;
import com.dandandog.modules.auth.entity.AuthUser;
import com.dandandog.modules.auth.entity.enums.UserType;
import com.dandandog.modules.auth.service.AuthUserService;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/8/27 11:01
 */
public class AuthUserPageAdapter extends AbstractPageAdapter<AuthUser> {

    private final String roleId;

    public AuthUserPageAdapter(String roleId) {
        this.roleId = roleId;
    }

    public IPage<AuthUser> paging(Page<AuthUser> page, QueryWrapper<AuthUser> queryWrapper) {
        AuthUserService service = (AuthUserService) getBaseService();
        return service.pageJoinByRole(page, queryWrapper);
    }

    @Override
    public void conditions(QueryWrapper<AuthUser> queryWrapper) {
        queryWrapper.lambda().eq(AuthUser::getType, UserType.USER);
        queryWrapper.eq(StrUtil.isNotBlank(roleId), "aur.role_id", roleId);
    }
}
