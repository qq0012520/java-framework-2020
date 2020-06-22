package com.tudog.graphqldemo01.api.book;


import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.entity.Book;
import com.tudog.graphqldemo01.model.BookInput;
import com.tudog.graphqldemo01.repository.AuthorRepository;
import com.tudog.graphqldemo01.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class BookMutation implements GraphQLMutationResolver{
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public Book addBook(BookInput book){
        Book entity = new Book();
        Author author = new Author();
        entity.setAuthor(author);
        return bookRepository.save(entity);
    }

}