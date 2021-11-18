package com.dandandog.blog.modules.admin.auth.web.faces.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.admin.auth.entity.AuthResource;
import com.dandandog.blog.modules.admin.auth.web.faces.vo.AuthResourceVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import com.dandandog.framework.mybatis.utils.MybatisUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

/**
 * @author Johnny
 */
@Mapper
public interface AuthResourceMapper extends IMapper<AuthResource, AuthResourceVo> {


    @Override
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "resourceParent")
    AuthResourceVo mapTo(AuthResource resource);


    @Named("resourceParent")
    default TreeNode resourceParent(String parentId) {
        BaseMapper<AuthResource> baseMapper = MybatisUtil.getOneMappersByModelClass(AuthResource.class);
        AuthResource resource = baseMapper.selectById(parentId);
        return new CheckboxTreeNode(resource);
    }

    @Named("resourceParent")
    default String resourceParent(TreeNode parent) {
        return parent != null && parent.getData() != null ? ((BaseEntity) parent.getData()).getId() : null;
    }


}
