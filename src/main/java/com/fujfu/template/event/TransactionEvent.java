package com.fujfu.template.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author Jun Luo
 * @date 2021/6/21 1:59 PM
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class TransactionEvent {
    /**
     * 这个对应的是 EventRecordDO.id
     */
    private String eventId;
}
