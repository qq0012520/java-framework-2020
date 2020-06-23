package com.tudog.graphqldemo01.api.book;


import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.entity.Book;
import com.tudog.graphqldemo01.repository.AuthorRepository;
import com.tudog.graphqldemo01.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class BookMutation implements GraphQLMutationResolver{
    
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;
   
    @Transactional
    public Book addBook(Book book){
        Author author = authorRepository.findById(book.getAuthorId()).get();
        book.setAuthor(author);
        return bookRepository.save(book);
    }

}