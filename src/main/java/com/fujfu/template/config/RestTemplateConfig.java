package com.fujfu.template.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author Jun Luo
 * @date 2020/5/25 4:29 PM
 */
@Configuration
@AllArgsConstructor
public class RestTemplateConfig {
    private final RestTemplateBuilder restTemplateBuilder;

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.setReadTimeout(Duration.ofSeconds(60)).setConnectTimeout(Duration.ofSeconds(60)).build();
    }
}