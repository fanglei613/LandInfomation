package com.jh.collection.service;

import com.github.pagehelper.PageInfo;
import com.jh.collection.entity.CollectionUser;
import com.jh.vo.ResultMessage;

/**
 * 用户采集任务接口
 * @version <1> 2018-11-15 xy: Created.
 */
public interface ICollectionUserService {

    /**
     * 查询采集任务
     * @param UserQuery
     * @return
     * @version <1> 2018-07-23 xy： Created.
     */
    PageInfo<CollectionUser> findByPage(CollectionUser UserQuery);

    /**
     * 采集用户注册
     * @param collectionUser
     * @return
     */
    public ResultMessage register(CollectionUser collectionUser) throws Exception;

    /**
     * 登录
     * @param collectionUser
     * @return
     */
    public ResultMessage login(CollectionUser collectionUser);

    /**
     * 采集用户退出当前登录
     * @return
     */
    public ResultMessage loginOut(String sessionId);

    /**
     * 找回密码
     * @param collectionUser
     * @return
     */
    public ResultMessage findPwd(CollectionUser collectionUser);

    /**
     * 重置密码
     * @param collectionUser
     * @return
     */
    public ResultMessage resetPwd(CollectionUser collectionUser);

    /**
     * 修改密码
     * @param collectionUser
     * @return
     */
    public ResultMessage modifyPwd(CollectionUser collectionUser);


    /**
     * 解绑手机（更换手机号）
     * @param collectionUser
     * @return
     */
    public ResultMessage resetPhone(CollectionUser collectionUser);


    /**
     * 采集用户登录检查
     * @param sessionId
     * @return
     */
    public ResultMessage loginCheck(String sessionId);


    /**
     * 采集用户关联微信号
     * @param collectionUser
     * @return
     */
    public ResultMessage relateWx(CollectionUser collectionUser);

    /**
     * 采集用户微信登录
     * @param wxId
     * @return
     */
    public ResultMessage wxLogin(String wxId);

}
