package com.dandandog.blog.web.admin.faces.mapper;

import com.dandandog.blog.web.admin.faces.vo.AuthUserVo;
import com.dandandog.framework.mapstruct.IMapper;
import com.dandandog.modules.blog.entity.BlogPersonal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @Author: JohnnyLiu
 * @Date: 2022/2/8 10:01
 */
@Mapper
public interface BlogPersonalMapper extends IMapper<BlogPersonal, AuthUserVo> {

    @Override
    @Mapping(target = "id", ignore = true)
    AuthUserVo mapTo(BlogPersonal blogPersonal);

    @Override
    @Mapping(target = "id", ignore = true)
    BlogPersonal mapFrom(AuthUserVo authUserVo);
}
