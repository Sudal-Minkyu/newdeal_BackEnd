package com.broadwave.backend.configs;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 호스트링크주입
    @Value("${newdeal.api.front_url}")
    private String front_url;

    @Value("${newdeal.api.front_protocol}")
    private String front_protocol;

    //  CORS에러 No 'Access-Control-Allow-Origin' header is present on the requested resource. 해결
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(front_protocol+"://"+front_url, front_protocol)
                .allowedHeaders("JWT_AccessToken","insert_id");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "uploadPath")
    public String uploadPath() {
        return uploadFileDir;
    }

    @Value("${newdeal.fileupload.url}")
    String uploadFileDir;

}