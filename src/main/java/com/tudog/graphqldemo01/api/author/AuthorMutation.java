package com.tudog.graphqldemo01.api.author;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class AuthorMutation implements GraphQLMutationResolver{

    @Autowired
    private AuthorRepository authorRepository;

    public Author addAuthor(AuthorInput input){
        return authorRepository.save(author);
    }
}