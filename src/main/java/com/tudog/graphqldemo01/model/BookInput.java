package com.tudog.graphqldemo01.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookInput{
    private Long id;
    private String name;
    private Integer pageCount;
    private Integer authorId;

    public BookInput(Long id, String name, Integer pageCount, Integer authorId) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.authorId = authorId;
    }

}