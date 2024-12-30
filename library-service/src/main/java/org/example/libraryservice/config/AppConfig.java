package org.example.libraryservice.config;

import kong.unirest.JsonObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public JsonObjectMapper jsonObjectMapper(){
        return new JsonObjectMapper();
    }
}
