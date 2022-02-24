package com.dandandog.modules.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import com.dandandog.modules.blog.entity.enums.GenderType;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/12/15 17:22
 */

@Getter
@Setter
@TableName("blog_personal")
public class BlogPersonal extends AuditableEntity {

    private String username;

    private String email;

    private String phone;

    private GenderType gender;

    private String avatarUrl;

    private String motto;

    private String homepage;

    private String address;

    public BlogPersonal() {

    }

    public BlogPersonal(String username) {
        this.username = username;
    }
}
