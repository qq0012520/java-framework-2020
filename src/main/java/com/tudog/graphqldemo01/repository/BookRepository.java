package com.tudog.graphqldemo01.repository;

import com.tudog.graphqldemo01.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long>{
    
}