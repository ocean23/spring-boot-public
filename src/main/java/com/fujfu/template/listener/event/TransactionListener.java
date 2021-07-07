package com.fujfu.template.listener.event;

import com.alibaba.fastjson.JSON;
import com.fujfu.template.constant.RedisKeyConstant;
import com.fujfu.template.entity.EventRecordSendDO;
import com.fujfu.template.event.TransactionEvent;
import com.fujfu.template.listener.mo.EventMO;
import com.fujfu.template.repo.EventRecordSendAutoRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;


/**
 * @author Jun Luo
 * @date 2021/6/21 1:59 PM
 */
@Component
@Slf4j
@AllArgsConstructor
public class TransactionListener {
    private final RabbitTemplate rabbitTemplate;
    private final EventRecordSendAutoRepo eventRecordSendAutoRepo;
    private final RedissonClient redissonClient;

    /**
     * 监听事务的 commit，一个事务成功的状态流转为 BEFORE_COMMIT -> AFTER_COMMIT -> AFTER_COMPLETION
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onEventHandler(TransactionEvent event) {
        log.debug("onEventHandler, event = {}", event);
        String eventId = event.getEventId();

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
            Optional<EventRecordSendDO> eventRecordSendDOOptional = eventRecordSendAutoRepo.findById(eventId);
            if (eventRecordSendDOOptional.isEmpty()) {
                log.error("未在 EventRecordSendDO 中找到对应的数据，eventId = {}", eventId);
                return;
            }
            EventRecordSendDO eventRecordSendDO = eventRecordSendDOOptional.get();
            if (eventRecordSendDO.isSent()) {
                log.debug("eventId = {} 的 EventRecordSendDO 消息已发送", eventId);
                return;
            }
            // 将事件通过 MQ 发送到对应的 key 中
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
