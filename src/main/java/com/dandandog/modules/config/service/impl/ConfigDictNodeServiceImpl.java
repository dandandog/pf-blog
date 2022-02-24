package com.dandandog.modules.config.service.impl;

import com.dandandog.modules.config.dao.ConfigDictNodeDao;
import com.dandandog.modules.config.entity.ConfigDictNode;
import com.dandandog.modules.config.service.ConfigDictNodeService;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 字典节点(AppDictNode)表服务实现类
 *
 * @author makejava
 * @since 2020-11-21 16:40:47
 */
@Service
public class ConfigDictNodeServiceImpl extends BaseServiceImpl<ConfigDictNodeDao, ConfigDictNode> implements ConfigDictNodeService {

}