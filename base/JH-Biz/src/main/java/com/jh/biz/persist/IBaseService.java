package com.jh.biz.persist;

import java.io.Serializable;
import java.util.List;
import com.github.pagehelper.PageInfo;

/**
* 通用服务，定义数据操作的常用方法
* 
* T 表示操作的数据实体类
* @version Hayden [2018-08-03 10:36:27] : Created.
*/
public interface IBaseService<T>{
	
	/**
	* 根据Example条件进行查询
	* 重点：这个查询支持通过Example类指定查询列，通过selectProperties方法指定查询列
	* @version Hayden 2018-08-08 17:41:37 : Created.
	*/
	public List<T> queryByExample(Object example);

	/**
	* 根据主键查询实体
	* @version Hayden 2018-08-08 17:43:47 : Created.
	*/
    public T queryById(Serializable id);
    
    //查询所有
    public List<T> queryAll();
    
    /**
	* 根据对象不为空的字段查询，多个不为空的字段用and联接
	* @version Hayden 2018-08-08 17:45:13 : Created.
	*/
    public List<T> queryListByWhere(T param);
    
    //查询记录数
    public Integer queryCount(T param);
    
    //分页
    public PageInfo<T> queryPageListByWhere(T param,Integer page,Integer rows);
    
    //查询一条记录
    public T queryOne(T param);
    
    //插入
    public Integer save(T param);
    
    //新增非空字段
    public Integer saveSelect(T param);
    
    //根据主键更新
    public Integer update(T param);
    
    //根据主键更新非空字段
    public Integer updateSelective(T param);
       
    //根据主键删除
    public Integer deleteById(Serializable id);
    
    //批量删除
    public Integer deleteByIds(Class<T> clazz,String idFieldName,List<Object> ids);

}

