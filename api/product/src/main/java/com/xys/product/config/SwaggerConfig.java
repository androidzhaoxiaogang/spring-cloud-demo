package com.xys.product.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * Swagger配置相关
 *
 * @author 摇光
 * @version 1.0
 * @Created on 2016/6/29
 * @Copyright:杭州安存网络科技有限公司 Copyright (c) 2016
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public static final String SECURITY_SCHEMA_O_AUTH_2 = "oauth2schema";
    public static final String AUTHORIZATION_SCOPE_GLOBAL = "openid";
    public static final String AUTHORIZATION_SCOPE_GLOBAL_DESC ="accessEverything";

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xys.product.service"))
                .paths(pathSelector())
                .build()
                .securitySchemes(newArrayList(securitySchema()))
                .securityContexts(newArrayList(securityContext()))
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .host("localhost:11113/api/product")
                .protocols(newHashSet("http"));
        return docket;
    }

    private OAuth securitySchema() {
        AuthorizationScope authorizationScope = new AuthorizationScope(AUTHORIZATION_SCOPE_GLOBAL, AUTHORIZATION_SCOPE_GLOBAL);
        LoginEndpoint loginEndpoint = new LoginEndpoint("http://gateway/api/uaa/oauth/token");
        GrantType grantType = new ImplicitGrant(loginEndpoint, "access_token");
        return new OAuth(SECURITY_SCHEMA_O_AUTH_2, newArrayList(authorizationScope), newArrayList(grantType));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(pathSelector())
                .build();
    }

    private Predicate<String> pathSelector(){
        return PathSelectors.any();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope(AUTHORIZATION_SCOPE_GLOBAL, AUTHORIZATION_SCOPE_GLOBAL_DESC);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference(SECURITY_SCHEMA_O_AUTH_2, authorizationScopes));
    }

    /**
     * api具体信息
     *
     * @return  api信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SPRING CLOUD DEMO - MICROSERVICE PERODUCT - RESTful APIs")
                .description("Spring Boot中使用Swagger2构建RESTful APIs")
                .termsOfServiceUrl("http://http://it.ancun-inc.com/")
                .contact("摇光")
                .version("1.0")
                .build();
    }

}
