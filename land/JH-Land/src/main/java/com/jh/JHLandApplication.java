package com.jh;

import com.jh.util.ceph.CephUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;

/**
 * Description:
 *
 * @version <1> 2018-07-14  lcw : Created.
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@EnableFeignClients
@EnableEurekaClient
public class JHLandApplication
{

//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }

    /**
     * 文件上传，定义临时文件目录
     * @return
     * @version<1> 2018-11-06 lijie : created. 解决linux上10天后临时目录被删除，文件不能上传的问题
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String absPath = CephUtils.getUploadTmpPath();
        factory.setLocation(absPath);
        return factory.createMultipartConfig();
    }


    public static void main(String[] args) {
        SpringApplication.run(JHLandApplication.class, args);
    }
}
