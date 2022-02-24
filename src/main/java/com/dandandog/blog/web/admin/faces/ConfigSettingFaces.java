package com.dandandog.blog.web.admin.faces;

import com.dandandog.blog.web.admin.faces.vo.ConfigSettingVo;
import com.dandandog.framework.faces.annotation.Faces;
import com.dandandog.framework.mapstruct.utils.MapperUtil;
import com.dandandog.modules.config.entity.ConfigSetting;
import com.dandandog.modules.config.service.ConfigSettingService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @Author: JohnnyLiu
 * @Date: 2022/2/24 17:58
 */
@Faces
public class ConfigSettingFaces {

    @Resource
    private ConfigSettingService configSettingService;


    public Collection<ConfigSettingVo> list() {
        List<ConfigSetting> list = configSettingService.list();
        return MapperUtil.mapToAll(list, ConfigSettingVo.class);

    }

    public Optional<ConfigSettingVo> getOptById(String id) {
        return Optional.ofNullable(configSettingService.getById(id))
                .map(entity -> MapperUtil.map(entity, ConfigSettingVo.class));
    }

    public void saveOrUpdate(ConfigSettingVo vo) {
        ConfigSetting entity = MapperUtil.map(vo, ConfigSetting.class);
        configSettingService.saveOrUpdate(entity);
    }

    public void removeByIds(String... idList) {
        configSettingService.removeByIds(Arrays.asList(idList));
    }
}
