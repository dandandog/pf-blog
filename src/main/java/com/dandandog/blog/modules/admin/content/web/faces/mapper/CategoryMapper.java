package com.dandandog.blog.modules.admin.content.web.faces.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.modules.admin.content.entity.BlogMetas;
import com.dandandog.blog.modules.admin.content.web.faces.vo.CategoryVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import com.dandandog.framework.mybatis.utils.MybatisUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/10 11:24
 */
@Mapper
public interface CategoryMapper extends IMapper<BlogMetas, CategoryVo> {

    @Override
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "resourceParent")
    CategoryVo mapTo(BlogMetas metas);


    @Named("resourceParent")
    default TreeNode resourceParent(String parentId) {
        BaseMapper<BlogMetas> baseMapper = MybatisUtil.getOneMappersByModelClass(BlogMetas.class);
        BlogMetas entity = baseMapper.selectById(parentId);
        return new CheckboxTreeNode(entity);
    }

    @Named("resourceParent")
    default String resourceParent(TreeNode parent) {
        return parent != null && parent.getData() != null ? ((BaseEntity) parent.getData()).getId() : null;
    }


}
