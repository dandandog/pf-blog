package com.dandandog.blog.security.mapper;

import cn.hutool.core.collection.CollectionUtil;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserGender;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserStatus;
import com.dandandog.blog.modules.admin.auth.entity.enums.UserType;
import com.dandandog.framework.mapstruct.model.MapperUrl;
import com.dandandog.framework.mapstruct.model.MapperVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/10/9 11:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCredentials extends MapperVo implements UserDetails {

    private String nickname;

    private String username;

    private String password;

    private String salt;

    private UserType type;

    private String email;

    private String phone;

    private UserStatus status = UserStatus.NORMAL;

    private UserGender gender = UserGender.UNKNOWN;

    private MapperUrl avatarUrl;

    private Collection<GrantedAuthority> authorities = CollectionUtil.newArrayList();

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
