package com.fujfu.template.repo;


import com.fujfu.template.entity.EventRecordSendDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Jun Luo
 * @date 2021/6/21 2:03 PM
 */
public interface EventRecordSendAutoRepo extends MongoRepository<EventRecordSendDO, String> {
    List<EventRecordSendDO> findAllBySent(boolean sent);
}
