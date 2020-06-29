package com.tudog.graphqldemo01.repository;

import java.util.List;

import com.tudog.graphqldemo01.entity.Author;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorRepository extends JpaRepository<Author,Long>{

    @EntityGraph(attributePaths = { "books" })
    Author getById(Long id);

    @EntityGraph(attributePaths = { "books" })
    @Query("select a from Author a")
    List<Author> getAll();

    @EntityGraph(attributePaths = { "books" })
    @Query("select a from Author a")
    Page<Author> getAll(Pageable pageable);
}