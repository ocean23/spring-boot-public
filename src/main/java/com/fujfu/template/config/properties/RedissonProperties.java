package com.fujfu.template.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Beldon
 * @create 2018-09-06 10:33
 */
@Component
@ConfigurationProperties(prefix = "redisson")
@Setter
@Getter
public class RedissonProperties {

    /** 等待节点回复命令的时间,该时间从命令发送成功时开始计时 **/
    private int timeout = 3000;
    private String address;
    private int database;
    private String password;
    /** 设置对于master节点的连接池中连接数最大为500 **/
    private int connectionPoolSize = 500;
    /**
     * 如果当前连接池里的连接数量超过了最小空闲连接数
     * 而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭
     * 并从连接池里去掉,时间单位是毫秒
     */
    private int idleConnectionTimeout = 10000;
    /** 同任何节点建立连接时的等待超时。时间单位是毫秒 **/
    private int connectTimeout = 30000;
    private int connectionMinimumIdleSize = 10;
    private int slaveConnectionPoolSize = 250;
    private int masterConnectionPoolSize = 250;
    private String[] sentinelAddresses;
    private String masterName;
    private String[] clusterAddress;
    private Integer scanInterval = 2000;

}
