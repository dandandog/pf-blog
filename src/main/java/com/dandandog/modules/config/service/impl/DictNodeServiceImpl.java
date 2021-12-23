package com.dandandog.modules.config.service.impl;

import com.dandandog.modules.config.dao.DictNodeDao;
import com.dandandog.modules.config.entity.DictNode;
import com.dandandog.modules.config.service.DictNodeService;
import com.dandandog.framework.mybatis.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 字典节点(AppDictNode)表服务实现类
 *
 * @author makejava
 * @since 2020-11-21 16:40:47
 */
@Service
public class DictNodeServiceImpl extends BaseServiceImpl<DictNodeDao, DictNode> implements DictNodeService {

}