/**
 * 
 */
package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**  
* @ClassName: Swagger2  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @author li chao
* @date 2016年12月16日 下午1:36:54 
* @version V1.0  
*/
@Configuration
@EnableSwagger2
public class Swagger2 {
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))
                .paths(PathSelectors.any())
                .build();
    }
    @SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("智营销基础服务接口文档")
                .termsOfServiceUrl("http://www.ejw.cn/")
                .contact("lichao")
                .version("1.0")
                .build();
    }
}
