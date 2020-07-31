package com.tudog.graphqldemo01.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.tudog.graphqldemo01.entity.base.BaseEntity;

import lombok.Data;

@Entity
@Data
public class BladeVisualMap extends BaseEntity{

    private String name;

    @Column(columnDefinition = "longtext")
    private String data;
}