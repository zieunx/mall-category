package com.mall.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Mall Category",
                "쇼핑몰 카테고리 기능 구현",
                "version 1.0",
                "",
                new Contact("zieunx", "https://github.com/zieunx", "zieunx95@gmail.com"),
                "",
                "https://naver.com",
                new ArrayList<>()
        );
    }

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("카테고리")
                .select()
                .apis(RequestHandlerSelectors.
                        basePackage("com.mall.server"))
                .paths(PathSelectors.ant("/category/**"))
                .build()
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, getResponseMessages());
    }

    private static List<ResponseMessage> getResponseMessages() {
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder()
                .code(200)
                .message("OK")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(404)
                .message("Not Found Error")
                .build());
        responseMessages.add(new ResponseMessageBuilder()
                .code(500)
                .message("Internal Server Error")
                .build());
        return responseMessages;
    }
}
