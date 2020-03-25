package com.jh.ttn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * description:
 * @version <1> 2018-04-27 cxw: Created.
 */
@SpringBootApplication
@EnableEurekaClient

public class JHTtnApp {


    public static  void main(String[] args){
        SpringApplication.run(JHTtnApp.class, args);
    }
}
