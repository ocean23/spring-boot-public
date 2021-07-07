package com.fujfu.template.job;

import com.alibaba.fastjson.JSON;
import com.fujfu.template.constant.RedisKeyConstant;
import com.fujfu.template.entity.EventRecordSendDO;
import com.fujfu.template.listener.mo.EventMO;
import com.fujfu.template.repo.EventRecordSendAutoRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 这里假设我们处在单实例环境中，如果你在多实例环境中请使用 PowerJob
 *
 * @author Jun Luo
 * @date 2021/6/21 2:29 PM
 */
@Component
@Slf4j
@AllArgsConstructor
public class EventHandlerSendJob {
    private final EventRecordSendAutoRepo eventRecordSendAutoRepo;
    private final RedissonClient redissonClient;
    private final RabbitTemplate rabbitTemplate;

    /**
     * 这里假设我们处在单实例环境中，如果你在多实例环境中请使用 PowerJob
     **/
    @Scheduled(fixedRate = 5000)
    public void push() {
        List<EventRecordSendDO> eventRecordSendDOList = eventRecordSendAutoRepo.findAllBySent(false);
        for (EventRecordSendDO eventRecordSendDO : eventRecordSendDOList) {
            sendSingleRecord(eventRecordSendDO);
        }
    }

    public void sendSingleRecord(EventRecordSendDO eventRecordSendDO) {
        log.debug("sendSingleRecord, data = {}", eventRecordSendDO);
        String eventId = eventRecordSendDO.getId();
        String lockKey = RedisKeyConstant.EVENT_RECORD_SEND.replaceAll("\\{eventId}", eventId);
        RLock lock = redissonClient.getLock(lockKey);
        /**
         * 加锁，防止 EventHandlerSendJob 和 onEventHandler 同时处理同一个 DO
         * 如果不加的话，可能一条消息发送两次，但其实也没什么关系，因为我们会在接收处做幂等处理
         */
        if (!lock.tryLock()) {
            log.info("the record is being processed, eventId = {}", eventId);
            return;
        }
        try {
            if (eventRecordSendDO.isSent()) {
                log.debug("eventId = {} 的 EventRecordSendDO 消息已发送", eventId);
                return;
            }
            // 将消息通过 MQ 发送到对应的 key 中
            EventMO eventMO = new EventMO();
            eventMO.setEventId(eventId);
            eventMO.setData(eventRecordSendDO.getData());
            rabbitTemplate.convertAndSend(eventRecordSendDO.getRoutingKey(), JSON.toJSONString(eventMO));
            eventRecordSendDO.setSent(true);
            eventRecordSendAutoRepo.save(eventRecordSendDO);
        } finally {
            lock.unlock();
        }
    }
}
