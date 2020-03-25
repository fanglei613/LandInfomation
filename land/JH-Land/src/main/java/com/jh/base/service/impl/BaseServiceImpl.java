package com.jh.base.service.impl;

import com.jh.base.repository.IBaseMapper;
import com.jh.base.service.IBaseService;
import com.jh.vo.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * description:实现类的基类
 * VO : 查询实体VO
 * T : 实体
 * PK ： 主键对应的类型
 * @version <1> 2017-12-07 lcw： Created.
 */
public abstract class BaseServiceImpl<VO, T, PK extends Serializable> implements IBaseService<VO, T, PK> {
    private static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
    protected abstract IBaseMapper<VO, T, PK> getDao();

    public ResultMessage save(T o) {
        getDao().save(o);
        return ResultMessage.success();
    }

    public ResultMessage delete(T model) {
        getDao().delete(model);
        return ResultMessage.success();
    }

    public ResultMessage update(T model) {
        getDao().update(model);
        return ResultMessage.success();
    }

    public ResultMessage getById(PK id) {
        T model =  getDao().getById(id);
        return ResultMessage.success(model);
    }

}
