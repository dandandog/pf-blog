package com.dandandog.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dandandog.framework.common.model.ITree;
import com.dandandog.framework.mybatis.entity.AuditableEntity;
import com.dandandog.modules.auth.entity.enums.ResourceTarget;
import com.dandandog.modules.auth.entity.enums.ResourceType;
import lombok.Getter;
import lombok.Setter;


/**
 * 系统资源表(SysResource)实体类
 *
 * @author JohnnyLiu
 * @since 2020-09-06 22:06:06
 */
@Getter
@Setter
@TableName("auth_resource")
public class AuthResource extends AuditableEntity implements ITree {
    private static final long serialVersionUID = 916304099916723584L;

    /**
     * 唯一标识
     */
    private String code;
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 父级uuid
     */
    private String parentId;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 权限标识（逗号分隔）
     */
    private String perms;
    /**
     * 类型（0：目录; 1：菜单; 2：按钮）
     */
    private ResourceType type;
    /**
     * 图标
     */
    private String icon;
    /**
     * 是否显示
     */
    private boolean display;
    /**
     * 打开方式
     */
    private ResourceTarget target;
    /**
     * 层级
     */
    private String level;

    /**
     * 是否是叶子节点
     */
    private boolean leaf;


}