package com.fujfu.template.config;

import com.fujfu.piece.service.RemoteCallService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jun Luo
 * @date 2020/5/25 10:57 AM
 */
@Configuration
@AllArgsConstructor
@DependsOn(value = {"restTemplate"})
public class RemoteCallServiceConfig {
    private final RestTemplate restTemplate;

    @Bean
    public RemoteCallService remoteCallService() {
        RemoteCallService remoteCallService = new RemoteCallService();
        remoteCallService.setRestTemplate(restTemplate);
        return remoteCallService;
    }
}
