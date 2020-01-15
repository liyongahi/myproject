package com.mybatisplus.config.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author LiYonghai
 * @version 1.0
 * @describe
 * @date 2020/1/9
 */
@Configuration
@EnableSwagger2
public class MySwagger {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mybatisplus.generator.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("代码生成器请求接口")
                        .description("代码生成器请求接口，详细信息......")
                        .version("1.0")
                        .contact(new Contact("li","","3117390634@qq.com"))
                        .license("123")
                        .licenseUrl("http://www.baidu.com")
                        .build());
    }

}
