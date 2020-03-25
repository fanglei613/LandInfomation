package com.jh.system.service.impl;

import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.base.service.impl.BaseServiceImpl;
import com.jh.system.entity.RelateRoleRes;
import com.jh.system.mapping.IRelateRoleResMapper;
import com.jh.system.service.IRelateRoleResService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 角色与资源关联实现类
 * @version <1> 2018-01-23 lcw : Created.
 */
@Transactional
@Service
public class RelateRoleResServiceImpl extends BaseServiceImpl<RelateRoleRes, RelateRoleRes, Integer> implements IRelateRoleResService {
    private Logger logger = Logger.getLogger(RelateRoleResServiceImpl.class);

    @Autowired
    private IRelateRoleResMapper relateRoleResMapper;

    @Override
    protected IBaseMapper<RelateRoleRes, RelateRoleRes, Integer> getDao() {
        return relateRoleResMapper;
    }
}
