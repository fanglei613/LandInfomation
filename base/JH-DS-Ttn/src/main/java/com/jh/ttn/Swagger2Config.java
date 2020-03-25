/*
Applcation doc 文档配置
@version <1> 2017-10-30 Hayden: Created.
**/
package com.jh.ttn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
	@Bean
	public Docket createRestApi(){
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.jh.ttn.controller"))
			.paths(PathSelectors.any())
			.build();
	}

	private ApiInfo apiInfo(){
		Contact contact = new Contact("JiaHe","","");
		return new ApiInfoBuilder()
			.title("作物分布数据集服务 RESTful API")
			.termsOfServiceUrl("http://www.datall.cn")
			.contact(contact)
			.version("1.0.0")
			.build();
	}
}