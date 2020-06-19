package com.tudog.graphqldemo01.entity;

import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
public class Book{
    private Integer id;
    private String name;
    private Integer pageCount;
    private Integer authorId;
    private Author author;

    public Book(Integer id, String name, Integer pageCount, Integer authorId) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.authorId = authorId;
    }

}