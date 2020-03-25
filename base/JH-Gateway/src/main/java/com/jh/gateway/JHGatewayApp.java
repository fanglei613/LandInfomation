package com.jh.gateway;

import com.jh.gateway.filter.LoginFilter;
import com.jh.gateway.filter.ProductFilter;
import com.jh.util.ceph.CephUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.MultipartConfigElement;


@SpringBootApplication
@EnableZuulProxy
public class JHGatewayApp 
{
	@Bean
	public CorsFilter corsConfig(){
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // 允许cookies跨域
		config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
		config.addAllowedHeader("*");// #允许访问的头信息,*表示全部
		config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
		config.addAllowedMethod("OPTIONS");// 允许提交请求的方法，*表示全部允许
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");// 允许Get的请求方法
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}


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



    @Bean
	public LoginFilter loginFilter(){
		return new LoginFilter();
	}

	@Bean
	public ProductFilter productFilter(){return new ProductFilter();}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

    public static void main(String[] args) {
		SpringApplication.run(JHGatewayApp.class, args);
	}
}
