package com.jh.biz.persist;

import java.io.Serializable;
import java.util.List;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**  
*自定义通用Mappper
*此接品不能被扫描到，否则会出错
* 
*@version <1> 2018-08-02 09:39:18 Hayden : Created.
*/
public interface IBaseMapper<T> extends Mapper<T>,MySqlMapper<T>{
	// /**
	// * 根据主键查询实体
	// * @version Hayden 2018-08-08 17:43:47 : Created.
	// */
	// public T findById(Serializable pk);

	// /**
	// * 根据对象不为空的字段查询，多个不为空的字段用and联接
	// * @version Hayden 2018-08-08 17:45:13 : Created.
	// */
	// public List<T> find(T t);

	// /**
	// * 根据Example条件进行查询
	// * 重点：这个查询支持通过Example类指定查询列，通过selectProperties方法指定查询列
	// * @version Hayden 2018-08-08 17:41:37 : Created.
	// */
	// public List<T> findByExample(Object example);
}