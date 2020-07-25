package com.tudog.graphqldemo01.api.author;

import java.util.List;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

public class AuthorQuery implements GraphQLQueryResolver{
    
    @Autowired
    private AuthorService authorService;

    public Author findById(Long id, DataFetchingEnvironment env){
        return authorService.findById(id, env);
    }

    public List<Author> listAuthor(DataFetchingEnvironment env){
        return authorService.findAll(env);
    }
    
    public Page<Author> pageAuthor(Integer page,Integer size,DataFetchingEnvironment env){
        return authorService.findAll(page, size, env);
    }

    public List<Author> findByFirstName(String firstName,DataFetchingEnvironment env){
        return authorService.findByFirstName(firstName, env);
    }
    
}