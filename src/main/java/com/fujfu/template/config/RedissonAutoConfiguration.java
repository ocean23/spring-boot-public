package com.fujfu.template.config;


import com.fujfu.template.config.properties.RedissonProperties;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Beldon
 * @create 2018-09-06 10:35
 */
@Configuration
@ConditionalOnClass(Config.class)
public class RedissonAutoConfiguration {
    @Autowired
    private RedissonProperties redssionProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config;
        if (StringUtils.isNotBlank(redssionProperties.getAddress())) {
            config = singleConfig();
        } else if (StringUtils.isNotBlank(redssionProperties.getMasterName())) {
            config = redissonSentinelConfig();
        } else {
            config = redissonClusterConfig();
        }
        config.setThreads(4);
        return Redisson.create(config);
    }

    /**
     * 哨兵模式自动装配
     * 暂时不会用到这个模式
     */
    private Config redissonSentinelConfig() {
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redssionProperties.getSentinelAddresses())
                .setMasterName(redssionProperties.getMasterName())
                .setTimeout(redssionProperties.getTimeout())
                .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());

        if (StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }
        return config;
    }

    /**
     * 集群模式自动装配
     * 暂时不会用到这个模式
     */
    private Config redissonClusterConfig() {
        Config config = new Config();
        ClusterServersConfig clusterServersConfig = config.useClusterServers()
                .setScanInterval(redssionProperties.getScanInterval())
                .setTimeout(redssionProperties.getTimeout())
                .addNodeAddress(redssionProperties.getClusterAddress());
        if (StringUtils.isNotBlank(redssionProperties.getPassword())) {
            clusterServersConfig.setPassword(redssionProperties.getPassword());
        }
        return config;
    }

    /**
     * 单机模式自动装配
     * 现在只会用这个模式
     */
    private Config singleConfig() {
        //创建配置
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redssionProperties.getAddress())
                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
                .setIdleConnectionTimeout(redssionProperties.getIdleConnectionTimeout())
                .setConnectTimeout(redssionProperties.getConnectTimeout())
                .setTimeout(redssionProperties.getTimeout())
                .setDatabase(redssionProperties.getDatabase())
                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());

        if (StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }
        return config;
    }

}
