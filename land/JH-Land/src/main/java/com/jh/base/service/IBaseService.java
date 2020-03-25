package com.jh.base.service;

import com.jh.vo.ResultMessage;

import java.io.Serializable;

/**
 * description:service接口基类，包含单表增删改查基本方法
 *
 * @version <1> 2017-12-07 lcw： Created.
 */
public interface IBaseService<VO, T,PK extends Serializable> {

    /**
     * 保存对象
     * @param model
     * @return
     * @version<1> 2017-12-09  lcw: created.
     */
    ResultMessage save(T model);

    /**
     * 删除对象记录
     * @param model
     * @version<1> 2017-12-09  lcw: created.
     */
    ResultMessage delete(T model);

    /**
     * 跟新对象
     * @param model
     * @version<1> 2017-12-09  lcw: created.
     */
    ResultMessage update(T model);

    /**
     * 通过ID查询对象
     * @param id
     * @return
     * @version<1> 2017-12-09  lcw: created.
     */
    ResultMessage getById(PK id);

}
