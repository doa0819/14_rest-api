package com.ohgiraffers.jpatest;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    // 내가 쓴 API 하나의 명세서로 서술 하겠다
    @Bean
    public OpenAPI openAPI(){

        return new OpenAPI()
                .components(new Components())
                .info(swaggerInfo());
    }


    private Info swaggerInfo(){
        return new Info()
                .title("doa API")
                .description("나홀로 연습을 해보자")
                .version("1.0.0");
    }

}
