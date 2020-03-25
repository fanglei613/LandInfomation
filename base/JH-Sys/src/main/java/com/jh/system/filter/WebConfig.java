//package com.jh.system.filter;
//
//import com.jh.common.utils.PropertyUtil;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * @description: 用于web访问的相关配置
// *  1.public void addInterceptors(InterceptorRegistry registry)
// *    添加系统拦截器链，当前添加了登陆验证拦截器
// * @version <1> 2018-01-12 cxj： Created.
// */
//@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {
//    public void addInterceptors(InterceptorRegistry registry) {
//        String interceptorUrl = PropertyUtil.getPropertiesForConfig("no_auth_login_url");
//        String[] urlArray =  interceptorUrl.split(",");
//
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(urlArray);
//        super.addInterceptors(registry);
//    }
//}
