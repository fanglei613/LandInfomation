package com.jh.biz.persist;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.List;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.jh.biz.persist.IBaseMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

public class BaseService<T> implements IBaseService<T>{
	// @Autowired
 //    private Mapper<T> mapping;

    @Autowired
    private IBaseMapper<T> mapper;

    /**
	* 根据Example条件进行查询
	* 重点：这个查询支持通过Example类指定查询列，通过selectProperties方法指定查询列
	* @version Hayden 2018-08-08 17:41:37 : Created.
	*/
	public List<T> queryByExample(Object example){
		return this.mapper.selectByExample(example);
	}

	//根据id查询实体
    public T queryById(Serializable id){
        return this.mapper.selectByPrimaryKey(id);
    }
    
    //查询所有
    public List<T> queryAll(){
        return this.mapper.select(null);
    }
    
    //条件查询
    public List<T> queryListByWhere(T param){
        return this.mapper.select(param);
    }
    
    //查询记录数
    public Integer queryCount(T param){
        return this.mapper.selectCount(param);
    }
    
    //分页
    public PageInfo<T> queryPageListByWhere(T param,Integer page,Integer rows){
        PageHelper.startPage(page, rows);
        List<T> list = this.queryListByWhere(param);
        return new PageInfo<T>(list);

    }
    
    //查询一条记录
    public T queryOne(T param){
        return this.mapper.selectOne(param);
    }
    
    //插入
    public Integer save(T param){
        return this.mapper.insert(param);
    }
    
    //新增非空字段
    public Integer saveSelect(T param){
        return this.mapper.insertSelective(param);
    }
    
    //根据主键更新
    public Integer update(T param){
        return this.mapper.updateByPrimaryKey(param);
    }
    
    //根据主键更新非空字段
    public Integer updateSelective(T param){
        return this.mapper.updateByPrimaryKeySelective(param);
    }
       
    //根据主键删除
    public Integer deleteById(Serializable id){
        return this.mapper.deleteByPrimaryKey(id);
    }     
    
    //批量删除
    public Integer deleteByIds(Class<T> clazz,String idFieldName,List<Object> ids){
        Example example = new Example(clazz);
        example.createCriteria().andIn(idFieldName, ids);
        return this.mapper.deleteByExample(example);
    }  
}