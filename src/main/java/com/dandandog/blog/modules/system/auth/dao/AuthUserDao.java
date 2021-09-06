package com.dandandog.blog.modules.system.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.system.auth.entity.AuthUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户表(SysUser)表数据库访问层
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Mapper
public interface AuthUserDao extends BaseMapper<AuthUser> {

}