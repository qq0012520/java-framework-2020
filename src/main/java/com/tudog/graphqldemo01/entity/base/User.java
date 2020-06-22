package com.tudog.graphqldemo01.entity.base;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class User extends BaseEntity{
    private String name;

    private String password;
    
}