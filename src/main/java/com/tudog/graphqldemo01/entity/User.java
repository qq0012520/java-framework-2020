package com.tudog.graphqldemo01.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.tudog.graphqldemo01.entity.base.BaseEntity;
import com.tudog.graphqldemo01.repository.tools.UserJobNumberGenerator;

import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;

import lombok.Data;

@Entity
@Table(
        indexes = {
                @Index(
                        name = "IDX_ACCOUNT",
                        columnList = "account"
                )
        }
)
@Data
public class User extends BaseEntity{

    //账户(唯一)
    @Column(unique = true)
    private String account;

    //密码
    private String password;

    //用户姓名,可重复
    private String name;

    //工号
    //@GeneratorType(type = UserJobNumberGenerator.class,when = GenerationTime.INSERT)
    private String jobNumber;

    public User() {
    }
    
    public User(String account, String password, String name) {
            this.account = account;
            this.password = password;
            this.name = name;
    }

}