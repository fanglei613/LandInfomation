package com.jh.system.service.impl;

import com.jh.constant.SysConstant;
import com.jh.enums.DataStatusEnum;
import com.jh.enums.DelStatusEnum;
import com.jh.system.base.repository.IBaseMapper;
import com.jh.system.base.service.impl.BaseServiceImpl;
import com.jh.util.PropertyUtil;
import com.jh.vo.ResultMessage;
import com.jh.system.entity.RelateAccountRole;
import com.jh.system.mapping.IRelateAccountRoleMapper;
import com.jh.system.service.IRelateAccountRoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账号与角色关联实现类
 * @version <1> 2018-01-23 lcw : Created.
 */
@Transactional
@Service
public class RelateAccountRoleServiceImpl extends BaseServiceImpl<RelateAccountRole, RelateAccountRole, Integer> implements IRelateAccountRoleService {
    private Logger logger = Logger.getLogger(RelateAccountRoleServiceImpl.class);

    @Autowired
    private IRelateAccountRoleMapper relateAccountRoleMapper;

    @Override
    protected IBaseMapper<RelateAccountRole, RelateAccountRole, Integer> getDao() {
        return relateAccountRoleMapper;
    }


    @Override
    public ResultMessage setDefaultPermission(Integer accountId) {
        Integer defaultRoleId = Integer.parseInt(PropertyUtil.getPropertiesForConfig("DEFAULT_PERMISSION_ID"));
        RelateAccountRole relateRole  = new RelateAccountRole();
        relateRole.setAccountId(accountId);
        relateRole.setRoleId(defaultRoleId);
        relateRole  = relateAccountRoleMapper.findByAccountId(relateRole);
        if(relateRole == null){
            RelateAccountRole rr = new RelateAccountRole();
            rr.setAccountId(accountId);
            rr.setRoleId(defaultRoleId);
            relateAccountRoleMapper.save(rr);
        }
        return ResultMessage.success();

    }




    /**
     * 添加账号角色关联信息
     * @param relateAccountRoleMap(accountId:账号ID,roleId :角色Id)
     * @return   ResultMessage
     * @version <1> 2018-05-09 cxw： Created.
     */
    @Override
    public ResultMessage addAccountRole(Map<String,Object> relateAccountRoleMap) {
        ResultMessage res = ResultMessage.success();
        int num = relateAccountRoleMapper.addAccountRole(relateAccountRoleMap);
        if(num<=0)
        {
            res = ResultMessage.fail(SysConstant.Msg_AccountRole_Is_Fail);
        }
        return res;
    }

    @Override
    public ResultMessage findRolesByAccountId(Integer accountId) {
        if (accountId == null){
            return ResultMessage.fail(SysConstant.Request_Param_Empty);
        }

        Map<String,Object> roleParams = new HashMap<String,Object>();
        roleParams.put("accountId",accountId);
        roleParams.put("dataStatus", DataStatusEnum.Valid.getCode());
        roleParams.put("delFlag", DelStatusEnum.Normal.getCode());
        List<Map<String,Object>> roleList =  relateAccountRoleMapper.findRoleByAccountId(roleParams);
        return ResultMessage.success(roleList);
    }
}
