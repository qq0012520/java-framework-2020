package com.tudog.graphqldemo01.api.book;


import com.tudog.graphqldemo01.entity.Book;
import com.tudog.graphqldemo01.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class BookMutation implements GraphQLMutationResolver{
    
    @Autowired
    private BookService bookService;

    public Book addBook(Book book){
        return bookService.addBook(book);
    }

    public Book updateBook(Book book){
        return bookService.save(book);
    }

    public boolean deleteBook(Long id){
        bookService.deleteById(id);
        return true;
    }

}