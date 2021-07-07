package com.fujfu.template.config.customizer;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 用于提供自动填充 BaseDO 中 createdBy 与 lastModifiedBy 字段，你需要实现 getCurrentAuditor
 *
 * @author Jun Luo
 * @date 2020/5/26 11:18 AM
 */
@Component
public class MongoAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }
}
