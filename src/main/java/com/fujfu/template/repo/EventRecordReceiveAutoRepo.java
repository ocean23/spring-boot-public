package com.fujfu.template.repo;

import com.fujfu.template.entity.EventRecordReceiveDO;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jun Luo
 * @date 2021/6/22 3:17 PM
 */
public interface EventRecordReceiveAutoRepo extends MongoRepository<EventRecordReceiveDO, String> {
    boolean existsByEventId(String eventId);
}
