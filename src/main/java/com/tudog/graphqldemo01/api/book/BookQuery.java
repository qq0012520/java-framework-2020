package com.tudog.graphqldemo01.api.book;

import com.tudog.graphqldemo01.entity.Book;
import com.tudog.graphqldemo01.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLQueryResolver;


@Component
public class BookQuery implements GraphQLQueryResolver{

    @Autowired
    private BookService bookService;

    public Book bookById(Long id){
        return bookService.findById(id);
    }

}