package com.fujfu.template.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Jun Luo
 * @date 2021/6/22 3:10 PM
 */
@Getter
@Setter
@ToString(callSuper = true)
@Document(collection = "event_record_receive")
public class EventRecordReceiveDO extends BaseDO {
    private String eventId;
}
