package com.jh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableEurekaClient
public class JHMapApplication
{
    public static void main(String[] args) {
		SpringApplication.run(JHMapApplication.class, args);
	}
}
