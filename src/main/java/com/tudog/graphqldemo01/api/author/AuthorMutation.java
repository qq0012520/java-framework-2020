package com.tudog.graphqldemo01.api.author;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;

import graphql.kickstart.tools.GraphQLMutationResolver;

public class AuthorMutation implements GraphQLMutationResolver{

    @Autowired
    private AuthorService authorService;

    public Author addAuthor(Author author){
        return authorService.save(author);
    }
}