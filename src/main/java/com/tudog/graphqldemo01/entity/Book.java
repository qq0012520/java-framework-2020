package com.tudog.graphqldemo01.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.tudog.graphqldemo01.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity{
    private String name;
    private Integer pageCount;
    
    @ManyToOne
    private Author author;
}