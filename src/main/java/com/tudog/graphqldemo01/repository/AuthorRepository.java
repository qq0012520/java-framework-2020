package com.tudog.graphqldemo01.repository;

import com.tudog.graphqldemo01.entity.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long>{
    
}