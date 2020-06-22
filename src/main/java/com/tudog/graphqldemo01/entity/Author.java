package com.tudog.graphqldemo01.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.tudog.graphqldemo01.entity.base.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author extends BaseEntity{
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

}
