package com.tudog.graphqldemo01.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.tudog.graphqldemo01.entity.base.BaseEntity;

import lombok.Data;

@Entity
@Data
public class BladeVisual extends BaseEntity{

    private String title;
    
    @Column(columnDefinition = "longtext")
    private String backgroundUrl;

    @ManyToOne
    private BladeVisualCategory category;

    @ManyToOne
    private BladeVisualConfig config;

    private String password;

    private Long createUser;

    private Long createDept;

    private Date createTime;

    private Long updateUser;

    private Date updateTime;

    @Column(nullable = false)
    private Byte status;

    @Column(nullable = false)
    private Byte is_deleted; //是否删除,1 是，0 否

    @Transient
    private Long categoryId;

    @Transient
    private Long configId;
}