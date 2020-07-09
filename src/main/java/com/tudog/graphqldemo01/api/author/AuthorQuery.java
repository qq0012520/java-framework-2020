package com.tudog.graphqldemo01.api.author;

import java.util.List;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.service.AuthorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AuthorQuery implements GraphQLQueryResolver{

    @Autowired
    private AuthorService authorService;

    public Author authorById(Long id, DataFetchingEnvironment env){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String username = authentication.getName();
        Object principal = authentication.getPrincipal();
        return authorService.findById(id, env);
    }

    public List<Author> listAuthor(DataFetchingEnvironment env){
        return authorService.findAll(env);
    }
    
    public Page<Author> pageAuthor(Integer page,Integer size,DataFetchingEnvironment env){
        return authorService.findAll(PageRequest.of(page, size), env);
    }

    public Author findByFirstName(String firstName,DataFetchingEnvironment env){
        return authorService.findByFirstName(firstName, env);
    }
    
}