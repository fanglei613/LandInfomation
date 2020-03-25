package com.jh.wechat.mapping;

import com.jh.wechat.entity.WXRelateAccountEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by XZG on 2018/4/27.
 */
@Mapper
public interface IWXRelateAccountMapper {


    /**
     * 添加微信用户关联信息
     * @param relateAccount
     * @return
     */
    Integer insertRelateAccount(WXRelateAccountEntity relateAccount);

    /**
     * 更具微信ID 查询关联的手机号
     * @param wxId
     * @return
     */
    WXRelateAccountEntity findWxUserByWxid(String wxId);

}
