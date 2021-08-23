package com.dandandog.blog.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.system.entity.AuthRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统角色表(SysRole)表数据库访问层
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Mapper
public interface AuthRoleDao extends BaseMapper<AuthRole> {

    @Select("select ar.* from auth_role ar left join auth_user_role aur on ar.id = aur.role_id where aur.user_id = #{userId}")
    List<AuthRole> findByUser(String userId);

}