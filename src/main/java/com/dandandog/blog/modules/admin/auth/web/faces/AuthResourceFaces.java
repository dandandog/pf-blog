package com.dandandog.blog.modules.admin.auth.web.faces;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dandandog.blog.common.model.MapperTreeDataModel;
import com.dandandog.blog.modules.admin.auth.entity.AuthResource;
import com.dandandog.blog.modules.admin.auth.service.AuthResourceService;
import com.dandandog.blog.modules.admin.auth.web.faces.adapter.AuthResourceTreeAdapter;
import com.dandandog.blog.modules.admin.auth.web.faces.vo.AuthResourceVo;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.faces.model.tree.TreeDataModel;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.framework.mybatis.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Optional;


/**
 * @Author: JohnnyLiu
 * @Date: 2021/9/1 9:44
 */
@Faces
public class AuthResourceFaces {

    @Resource
    private AuthResourceService resourceService;


    public Optional<AuthResourceVo> getOptById(String id) {
        return Optional.ofNullable(resourceService.getById(id)).map(entity -> MapperUtil.map(entity, AuthResourceVo.class));
    }

    public TreeDataModel findDataModel() {
        return MapperTreeDataModel.getInstance(new AuthResourceTreeAdapter(), AuthResourceVo.class);
    }

    @Transactional
    public void saveOrUpdate(AuthResourceVo vo) {
        AuthResource entity = MapperUtil.map(vo, AuthResource.class);
        resourceService.lambdaUpdate().set(AuthResource::isLeaf, false).eq(BaseEntity::getId, entity.getParentId());
        resourceService.saveOrUpdate(entity);
    }

    public void removeByIds(String[] ids) {
        resourceService.removeByIds(Arrays.asList(ids));
    }

    public void updateByFiled(String id, String field, Object newValue) {
        resourceService.update(new UpdateWrapper<AuthResource>().set(field, newValue).eq("id", id));
    }
}


