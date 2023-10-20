package com.blog.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";

  private ApiKey apiKey(){
      return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
  }
  
  private ApiInfo apiInfo(){
      return new ApiInfo(
              "Spring Boot Blog REST APIs",
              "Spring Boot Blog REST API Documentation",
              "1",
              "Terms of service",
              new Contact("Karthick", "www.postdoubt.net", "karthick@gmail.com"),
              "License of API",
              "API license URL",
              Collections.emptyList()
      );
  }
  @Bean
  public Docket api(){
      return new Docket(DocumentationType.SWAGGER_2)
              .apiInfo(apiInfo())
              .securityContexts(Arrays.asList(securityContext()))
              .securitySchemes(Arrays.asList(apiKey()))
              .select()
              .apis(RequestHandlerSelectors.any())
              .paths(PathSelectors.any())
              .build();
  }
  private SecurityContext securityContext(){
      return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }
  private List<SecurityReference> defaultAuth(){
      AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
      AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
      authorizationScopes[0] = authorizationScope;
      return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
  }
	
	
	
	
	
	
	
	
//	private static final String BASIC_AUTH = "basicAuth";
//  private static final String BEARER_AUTH = "Bearer";
//
//  @Bean
//  public Docket api() {
//      return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.springboot.blog")).paths(PathSelectors.any()).build().apiInfo(apiInfo())
//              .securitySchemes(securitySchemes()).securityContexts(List.of(securityContext()));
//  }
//
//  private ApiInfo apiInfo() {
//      return new ApiInfo("POST REST API", "POST API to perform CRUD opertations", "1.0", "Terms of service",
//              new Contact("Java Chinna", "www.javachinna.com", "java4chinna@gmail.com"), "License of API", "API license URL", Collections.emptyList());
//  }
//
//  private List<SecurityScheme> securitySchemes() {
//      return List.of(new BasicAuth(BASIC_AUTH), new ApiKey(BEARER_AUTH, "Authorization", "header"));
//  }
//
//  private SecurityContext securityContext() {
//      return SecurityContext.builder().securityReferences(List.of(basicAuthReference(), bearerAuthReference())).forPaths(PathSelectors.ant("/api/posts/**")).build();
//  }
//
//  private SecurityReference basicAuthReference() {
//      return new SecurityReference(BASIC_AUTH, new AuthorizationScope[0]);
//  }
//
//  private SecurityReference bearerAuthReference() {
//      return new SecurityReference(BEARER_AUTH, new AuthorizationScope[0]);
//  }
}
