package com.fujfu.template.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 事件记录表
 *
 * @author Jun Luo
 * @date 2021/6/21 1:52 PM
 */
@Getter
@Setter
@ToString(callSuper = true)
@Document(collection = "event_record_send")
public class EventRecordSendDO extends BaseDO {
    /**
     * 需要发送到哪里去, MQ 的 routingKey
     */
    private String routingKey;
    /**
     * 数据
     */
    private String data;

    /**
     * 是否已发送
     */
    private boolean sent;
}
