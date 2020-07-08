package com.tudog.graphqldemo01.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.tudog.graphqldemo01.entity.Book;

public interface BookRepository extends EntityGraphJpaRepository<Book,Long>{
    
}