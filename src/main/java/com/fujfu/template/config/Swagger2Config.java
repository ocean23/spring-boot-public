package com.fujfu.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger 配置
 * https://www.jianshu.com/p/6e5ee9dd5a61
 * <p>
 * Swagger URL: /swagger-ui/index.html
 *
 * @author Beldon.
 */
@Configuration
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fujfu"))
                .paths(PathSelectors.any())
                .build();
    }

    // TODO: 请修改以下信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("应用名称")
                .description("应用描述")
                .termsOfServiceUrl("http://www.fujfu.com/")
                .contact(new Contact("富金富", "http://www.fujfu.com/", "fujfu@fujfu.com"))
                .version("1.0")
                .build();
    }
}
