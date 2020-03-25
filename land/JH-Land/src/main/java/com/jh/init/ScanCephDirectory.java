package com.jh.init;

import com.jh.util.ceph.CephUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;

/**
 * Description: 系统初始化执行类：用户初始化一些配置参数信息
 * @version <1> 2018-03-12  lcw : Created.
 */
@Configuration
public class ScanCephDirectory {

    /**
     * 分布式存储服务目录初始化判断：若不存在存储目录，则需创建
     *   配置文件中增加对应的ceph目录后，需修改initBaseCephDirectory()方法，添加对应的路径检索
     * @return
     */
    @Bean
    public boolean init(){
//        CephUtils.initBaseCephDirectory();
//        System.out.println(new Date(System.currentTimeMillis()) + " 分布式存储服务目录初始化完毕");
        return true;

    }

}
