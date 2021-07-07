package com.fujfu.template.config;

import com.fujfu.piece.service.RawRemoteCallService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jun Luo
 * @date 2021/1/26 2:59 PM
 */
@Configuration
@AllArgsConstructor
@DependsOn(value = {"restTemplate"})
public class RawRemoteCallServiceConfig {
    private final RestTemplate restTemplate;

    @Bean
    public RawRemoteCallService rawRemoteCallService() {
        RawRemoteCallService rawRemoteCallService = new RawRemoteCallService();
        rawRemoteCallService.setRestTemplate(restTemplate);
        return rawRemoteCallService;
    }
}
