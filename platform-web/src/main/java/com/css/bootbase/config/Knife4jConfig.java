package com.css.bootbase.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class Knife4jConfig {

    public static final String VERSION = "1.0.0";

    @Bean
    public Docket createRestApi() {
        //设置请求头参数
        return new Docket(DocumentationType.SWAGGER_2)
                //.enable(true)
                .apiInfo(apiInfo())
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();
    }

    //logintoken
    private ApiKey apiKey() {
        return new ApiKey("authorization", "authorization", "header");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("智能议案辅助工具")
                .description("智能议案辅助工具API接口文档")
                .contact(new Contact("RD-INTELLI-TOOL", "http://localhost:8080/doc.html", ""))
                .version(VERSION).build();
    }
}
