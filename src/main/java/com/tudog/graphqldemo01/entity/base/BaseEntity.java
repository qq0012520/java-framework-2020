package com.tudog.graphqldemo01.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@MappedSuperclass
public abstract class BaseEntity{
    public static final String LAST_MODIFIED_TIME_FIELD = "lastModifiedTime";

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //创建时间
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createTime;

    //最后修改时间
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.UpdateTimestamp
    private Date lastModifiedTime;
    
}