package com.tudog.graphqldemo01.api.author;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLQueryResolver;

@Component
public class AuthorQuery implements GraphQLQueryResolver{
    @Autowired
    private AuthorRepository authorRepository;

    public Author authorById(Long id){
        return authorRepository.findById(id).get();
    }
}