package com.tudog.graphqldemo01.api.author;

import com.tudog.graphqldemo01.api.book.BookQuery;
import com.tudog.graphqldemo01.entity.Author;

import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class AuthorQuery implements GraphQLQueryResolver{
    public Author authorById(Integer id){
        return BookQuery.authors.get(0);
    }
}