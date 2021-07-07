package com.fujfu.template.constant;

/**
 * @author Jun Luo
 * @date 2021/6/22 6:00 PM
 */
public interface RedisKeyConstant {

    /**
     * TODO: 修改此处
     *
     * 本项目前缀
     */
    String TEMPLATE_PREFIX = "template";

    /**
     * 发送事件处理
     */
    String EVENT_RECORD_SEND = TEMPLATE_PREFIX + ":eventRecordSend:" + "{eventId}";

    /**
     * 接收事件处理
     */
    String EVENT_RECORD_RECEIVE = TEMPLATE_PREFIX + ":eventRecordReceive:" + "{eventId}";
}
