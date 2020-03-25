package com.jh.collection.mapping;

import com.jh.collection.entity.CollectionUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectionUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CollectionUser record);

    int insertSelective(CollectionUser record);

    CollectionUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CollectionUser record);

    int updateByPrimaryKey(CollectionUser record);

    List<CollectionUser> findCollectionUserByPage(CollectionUser collectionUser);

    CollectionUser getCollectionUserByPhone(String phone);

    CollectionUser getCollectionUserByWxId(String wxId);
}