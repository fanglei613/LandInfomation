package com.jh.base.repository;

import java.io.Serializable;
import java.util.List;

/**
 * description: Mapper基类
 *
 * @version <1> 2017-12-09 lcw： Created.
 */

public interface IBaseMapper<VO, T, PK extends Serializable> extends Serializable{

        /**
         * 保存对象
         *
         * @param o
         * @return
         */
        void save(T o);

        /**
         * 删除对象记录
         *
         * @param model
         */
        void delete(T model);

        /**
         * 通过ID查询对象
         *
         * @param id
         * @return
         */
        T getById(PK id);

        /**
         * 更新对象
         *
         * @param model
         */
        void update(T model);

        /**
        * 分页查询
        * @param vo
        * @return
        */
        List<T> findByPage(VO vo);


}
