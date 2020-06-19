package com.tudog.graphqldemo01.api.book;


import com.tudog.graphqldemo01.model.BookInput;

import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class BookMutation implements GraphQLMutationResolver{
    
    public boolean addBook(BookInput book){
        System.out.println(book);
        return true;
    }

}