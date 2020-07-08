package com.tudog.graphqldemo01.repository;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.tudog.graphqldemo01.entity.Author;

public interface AuthorRepository extends EntityGraphJpaRepository<Author,Long>{
    Author findByFirstName(String firstName,EntityGraph entityGraph);
}