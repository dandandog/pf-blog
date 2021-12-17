package com.dandandog.blog.web.admin.faces.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dandandog.blog.web.admin.faces.vo.BlogCategoryVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import com.dandandog.framework.mybatis.utils.MybatisUtil;
import com.dandandog.modules.blog.entity.BlogMeta;
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
public interface BlogCateMapper extends IMapper<BlogMeta, BlogCategoryVo> {

    @Override
    @Mapping(target = "parent", source = "parentId", qualifiedByName = "resourceParent")
    BlogCategoryVo mapTo(BlogMeta metas);


    @Named("resourceParent")
    default TreeNode resourceParent(String parentId) {
        BaseMapper<BlogMeta> baseMapper = MybatisUtil.getOneMappersByModelClass(BlogMeta.class);
        BlogMeta entity = baseMapper.selectById(parentId);
        return new CheckboxTreeNode(entity);
    }

    @Named("resourceParent")
    default String resourceParent(TreeNode parent) {
        return parent != null && parent.getData() != null ? ((BaseEntity) parent.getData()).getId() : null;
    }


}
