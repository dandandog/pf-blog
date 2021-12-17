package com.dandandog.common.config.security.dto;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.dandandog.framework.common.model.IVo;
import com.dandandog.modules.auth.entity.enums.UserState;
import com.dandandog.modules.auth.entity.enums.UserType;
import com.dandandog.modules.blog.entity.enums.GenderType;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/10/9 11:12
 */
@Data
public class UserCredentials implements UserDetails, IVo {

    private String id;

    private String username;

    private String password;

    private String salt;

    private UserType type;

    private UserState status = UserState.NORMAL;

    private String nickname;

    private String email;

    private String phone;

    private GenderType gender;

    private String avatarUrl;

    private String motto;

    private String homepage;

    private String address;

    private LocalDateTime expiredTime;

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
        return this.expiredTime == null || LocalDateTimeUtil.now().isAfter(this.expiredTime);
    }

    @Override
    public boolean isAccountNonLocked() {
        return ObjectUtil.equal(status, UserState.NORMAL);
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
