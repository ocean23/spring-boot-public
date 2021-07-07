package com.fujfu.template.service;

import com.fujfu.piece.exception.UnexpectedException;
import com.fujfu.template.entity.EventRecordReceiveDO;
import com.fujfu.template.entity.EventRecordSendDO;
import com.fujfu.template.event.TransactionEvent;
import com.fujfu.template.repo.EventRecordReceiveAutoRepo;
import com.fujfu.template.repo.EventRecordSendAutoRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author Jun Luo
 * @date 2021/6/21 2:22 PM
 */
@AllArgsConstructor
@Service
@Slf4j
public class EventRecordService {
    private final EventRecordSendAutoRepo eventRecordSendAutoRepo;
    private final EventRecordReceiveAutoRepo eventRecordReceiveAutoRepo;
    private final ApplicationEventPublisher publisher;

    /**
     * 发布一个 EventRecord，请记住，你需要在调用此函数的地方启用 Transaction
     *
     * @param routingKey MQ key
     * @param data       数据
     */
    public void publishEventRecord(String routingKey, String data) {
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            log.error("This method must be called in a transaction.");
            throw new UnexpectedException("This method must be called in a transaction.");
        }
        EventRecordSendDO eventRecordSendDO = new EventRecordSendDO();
        eventRecordSendDO.setRoutingKey(routingKey);
        eventRecordSendDO.setData(data);
        eventRecordSendDO.setSent(false);
        eventRecordSendAutoRepo.save(eventRecordSendDO);
        publisher.publishEvent(new TransactionEvent(eventRecordSendDO.getId()));
    }

    /**
     * 因为挺多地方都需要用到这个函数，所以我把它抽到这里
     */
    public void markEventReceived(String eventId) {
        EventRecordReceiveDO eventRecordReceiveDO = new EventRecordReceiveDO();
        eventRecordReceiveDO.setEventId(eventId);
        eventRecordReceiveAutoRepo.save(eventRecordReceiveDO);
    }
}
