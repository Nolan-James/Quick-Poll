package me.nolanjames.quickpoll;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("me.nolanjames.quickpoll.controller"))
                .paths(PathSelectors.regex("/polls/*.*|/votes/*.*|/computeresult/*.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "QuickPoll REST API",
                "QuickPoll Api for creating and managing polls",
                "http://example.com/terms-of-service",
                "Terms of service",
                new Contact("James Nolan", "www.nolanjames.me", "james@nolanjames.me"),
                "MIT License", "http://opensource.org/licenses/MIT", Collections.emptyList());
    }
}