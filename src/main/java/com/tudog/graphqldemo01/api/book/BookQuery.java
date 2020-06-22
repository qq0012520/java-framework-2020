package com.tudog.graphqldemo01.api.book;

import java.util.Arrays;
import java.util.List;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.entity.Book;
import com.tudog.graphqldemo01.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLQueryResolver;


@Component
public class BookQuery implements GraphQLQueryResolver{

    @Autowired
    private BookRepository bookRepository;

    public Book bookById(Long id){
        return bookRepository.findById(id).get();
    }

}