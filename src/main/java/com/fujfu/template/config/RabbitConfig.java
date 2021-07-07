package com.fujfu.template.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.util.ErrorHandler;


/**
 * @author ocean
 * 为什么我们需要这个配置 http://wiki.fk.fjf.com/#/backend/java?id=rabbitmq-%e9%94%99%e8%af%af%e9%87%8d%e8%af%95%e9%97%b4%e9%9a%94%e4%b8%8e%e9%94%99%e8%af%af%e5%91%8a%e8%ad%a6
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class RabbitConfig {

    private final RabbitTemplate rabbitTemplate;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(new CustomErrorHandler());
        factory.setAdviceChain(retryInterceptor());
        return factory;
    }

    public RetryOperationsInterceptor retryInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .backOffOptions(1000, 2, 5000)
                .maxAttempts(5)
                .recoverer(new CustomerRecover())
                .build();
    }

    private static class CustomErrorHandler implements ErrorHandler {
        @Override
        public void handleError(Throwable throwable) {
            log.error("RabbitMQ 消费消息时发生了异常", throwable);
        }
    }

    class CustomerRecover extends RejectAndDontRequeueRecoverer {
        @Override
        public void recover(Message message, Throwable cause) {
            // 在这里将消息发回队列
            rabbitTemplate.convertAndSend(message.getMessageProperties().getReceivedExchange(), message.getMessageProperties().getReceivedRoutingKey(), message.getBody());
            super.recover(message, cause);
        }
    }
}

