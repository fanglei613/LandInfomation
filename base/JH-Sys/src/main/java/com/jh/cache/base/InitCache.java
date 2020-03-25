package com.jh.cache.base;

import com.jh.cache.service.ICacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @description: 启动加载缓存
 * @version <1> 2018-01-17 cxj： Created.
 */
@Component
public class InitCache implements ApplicationRunner {

    @Autowired
    private ICacheService cache;

    @Override
    @Async
    public void run(ApplicationArguments arg) throws Exception {
        cache.initAllDataCache();
    }

}
