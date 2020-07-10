package com.tudog.graphqldemo01.repository;

import java.util.List;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.tudog.graphqldemo01.entity.Author;

public interface AuthorRepository extends EntityGraphJpaRepository<Author,Long>{
    List<Author> findByFirstName(String firstName,EntityGraph entityGraph);

    List<Author> findByLastName(String lastName,EntityGraph entityGraph);
}