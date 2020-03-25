/**
* 服务注册类，用于注册微服务。
* @@version <1> 2018-04-24 10:10:37 Hayden : Created.
**/

package com.jh.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class JHServerApp{
	public static void main(String[] args){
		SpringApplication.run(JHServerApp.class,args);
	}
}