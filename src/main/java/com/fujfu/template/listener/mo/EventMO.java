package com.fujfu.template.listener.mo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jun Luo
 * @date 2021/6/22 3:13 PM
 */
@Getter
@Setter
@ToString
public class EventMO {
    /**
     * 事件 ID
     */
    private String eventId;

    /**
     * 事件数据
     */
    private String data;
}
