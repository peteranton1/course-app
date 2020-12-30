package io.javabrains.springbootquickstart.courseapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class BeanConfig {
    @Bean
    public Random randomObjectCreation() {
        return new Random();
    }
}
