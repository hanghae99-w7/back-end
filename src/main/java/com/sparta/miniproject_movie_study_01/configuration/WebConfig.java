package com.sparta.miniproject_movie_study_01.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000","http://54.180.89.34")
                    .allowedMethods("GET","POST","DELETE","PUT")
                    .exposedHeaders("Authorization","Refresh-Token")
                    .allowedHeaders("*")
                    .allowCredentials(true);//make client read header("jwt-token")
        }

}
