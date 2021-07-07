package com.fujfu.template.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.*;

import java.io.Serializable;

/**
 * @author Jun Luo
 * @date 2020/5/25 5:01 PM
 */
@Getter
@Setter
@ToString
@FieldNameConstants
public abstract class BaseDO implements Serializable {
    @Id
    private String id;
    @CreatedDate
    private Long createdDate;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private Long lastModifiedDate;
    @LastModifiedBy
    private String lastModifiedBy;
}
