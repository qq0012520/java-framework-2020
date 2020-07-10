package com.tudog.graphqldemo01.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.tudog.graphqldemo01.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{

    //账户(唯一)
    @Column(unique = true)
    private String account;

    //密码
    private String password;

    //用户姓名,可重复
    private String name;
}