package com.dandandog.modules.auth.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dandandog.modules.auth.entity.AuthUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 系统用户表(SysUser)表数据库访问层
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Mapper
public interface AuthUserDao extends BaseMapper<AuthUser> {
    @Select("select au.* from auth_user au left join auth_user_role aur on au.id = aur.user_id ${ew.customSqlSegment}")
    IPage<AuthUser> pageJoinByRole(Page<AuthUser> page, @Param(Constants.WRAPPER) Wrapper<AuthUser> wrapper);


}