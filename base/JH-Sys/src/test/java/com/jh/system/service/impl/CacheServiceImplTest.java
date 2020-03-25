package com.jh.system.service.impl;

import com.jh.system.entity.Dict;
import com.jh.cache.service.ICacheService;
import com.jh.system.entity.InitRegion;
import com.jh.system.entity.PermAccount;
import com.jh.util.JsonUtils;
import com.jh.vo.ResultMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by lijie on 2019/2/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheServiceImplTest {

    @Autowired
    private ICacheService cacheService;

    @Test
    public void queryNameByDictId() throws Exception {

        ResultMessage rm = cacheService.queryNameByDictId(4000);
        System.out.println(JsonUtils.objectToJson(rm));
    }

    @Test
    public void querySubListByDictId() throws Exception {

        ResultMessage rm = cacheService.findSubListByDictId(4000);
        List<Dict> list=(List<Dict>)rm.getData();
        System.out.println(JsonUtils.objectToJson(list));
    }

    @Test
    public void initDictCache() throws Exception {

       //cacheService.initDictCache();
    }

    @Test
    public void queryNameByRegionId() throws Exception {

        ResultMessage rm = cacheService.queryNameByRegionId(3102000031L);
        System.out.println(JsonUtils.objectToJson(rm));
    }

    @Test
    public void querySubListByRegionId() throws Exception {

        ResultMessage rm = cacheService.findSubListByRegionId(3102000031L);
        List<InitRegion> list=(List<InitRegion>)rm.getData();
        System.out.println(JsonUtils.objectToJson(list));
    }

    @Test
    public void initRegionCache() throws Exception {

        //cacheService.initRegionCache();
    }

    @Test
    public void queryNameByAccountId() throws Exception {

        ResultMessage rm = cacheService.queryNameByAccountId(274);
        System.out.println(JsonUtils.objectToJson(rm));
    }

    @Test
    public void queryUserByAccountId() throws Exception {

        ResultMessage rm = cacheService.findUserByAccountId(141);
        PermAccount permAccount=(PermAccount)rm.getData();
        System.out.println(JsonUtils.objectToJson(permAccount));
    }

    @Test
    public void initAccountCache() throws Exception {

        //cacheService.initUserCache();
    }



}