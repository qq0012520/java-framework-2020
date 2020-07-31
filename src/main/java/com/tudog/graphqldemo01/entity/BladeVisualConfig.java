package com.tudog.graphqldemo01.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.tudog.graphqldemo01.entity.base.BaseEntity;

import lombok.Data;


@Entity
@Data
public class BladeVisualConfig extends BaseEntity{

    @OneToMany(mappedBy = "config")
    private List<BladeVisual> bladeVisual;

    @Column(columnDefinition = "text")
    private String detail;

    @Column(columnDefinition = "longtext")
    private String component;

}