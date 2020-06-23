package com.tudog.graphqldemo01.api.author;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import graphql.kickstart.tools.GraphQLMutationResolver;

@Component
public class AuthorMutation implements GraphQLMutationResolver{

    @Autowired
    private AuthorRepository authorRepository;


    @Transactional
    public Author addAuthor(Author author){
        return authorRepository.save(author);
    }
}