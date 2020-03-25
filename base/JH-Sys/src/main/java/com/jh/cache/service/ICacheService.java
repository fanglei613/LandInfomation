package com.jh.cache.service;

import com.jh.system.entity.InitRegion;
import com.jh.system.model.DictParam;
import com.jh.vo.ResultMessage;

/**
 * @description: 数据缓存接口
 * @version <1> 2019-02-12 lijie： Created.
 */
public interface ICacheService{

    /**
     *根据字典ID查询名称
     * @return dictId
     * @version <1> 2019-02-12 lijie： Created.
     */
    ResultMessage queryNameByDictId(Integer dictId);

    /**
     *根据字典ID查询编码
     * @return dictId
     * @version <1> 2019-02-12 lijie： Created.
     */
    ResultMessage queryCodeByDictId(Integer dictId);


    /**
     *根据字典ID查询子集列表
     * @return dictId
     * @version <1> 2019-02-12 lijie： Created.
     */
    ResultMessage findSubListByDictId(Integer dictId);


    /**
     *根据区域ID查询名称
     * @return regionId
     * @version <1> 2019-02-12 lijie： Created.
     */
    ResultMessage queryNameByRegionId(Long regionId);

    /**
     *根据区域ID查询编码
     * @return regionId
     * @version <1> 2019-02-12 lijie： Created.
     */
    ResultMessage queryCodeByRegionId(Long regionId);


    /**
     *根据区域ID查询子集列表
     * @return regionId
     * @version <1> 2019-02-12 lijie： Created.
     */
    ResultMessage findSubListByRegionId(Long regionId);

    /**
     *根据用户ID查询名称
     * @return accountId
     * @version <1> 2019-02-12 lijie： Created.
     */
    ResultMessage queryNameByAccountId(Integer accountId);

    /**
     *根据用户ID查询子集列表
     * @return regionId
     * @version <1> 2019-02-12 lijie： Created.
     */
    ResultMessage findUserByAccountId(Integer accountId);

    /**
     * 初始化数据字典缓存
     * @version <1> 2019-02-12 lijie： Created.
     */
    void initDictCache();

    /**
     * 初始化区域数据缓存
     * @version <1> 2019-02-12 lijie： Created.
     */
    void initRegionCache();

    /**
     * 初始化用户缓存
     * @version <1> 2019-02-12 lijie： Created.
     */
    void initUserCache();

    /**
     * 初始化所有数据缓存
     * @version <1> 2019-02-12 lijie： Created.
     */
    void initAllDataCache();

    /**
     * 获取缓存最后更新时间
     * @version <1> 2019-02-12 lijie： Created.
     */
    ResultMessage getCacheLastTime();

    /**
     * 新增和修改字典时，修改缓存
     * parentId 父Id
     */
    ResultMessage updateCacheDict(DictParam dict, Long parentId,boolean isDel);

    /**
     * 新增和修改区域时，修改缓存
     * parentId 父Id
     */
    ResultMessage updateCacheRegion(InitRegion region,boolean isDel);

    /**
     * 新增和修改用户时，修改缓存
     * parentId 父Id
     */
    ResultMessage updateCacheUser(Integer accountId);

    ResultMessage queryAllLeaf(Integer parentId);
}
