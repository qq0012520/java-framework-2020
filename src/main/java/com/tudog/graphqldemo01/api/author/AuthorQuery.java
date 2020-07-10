package com.tudog.graphqldemo01.api.author;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.service.AuthorService;
import com.tudog.graphqldemo01.tools.HttpTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AuthorQuery implements GraphQLQueryResolver{

    @Autowired
    private AuthorService authorService;

    public Author authorById(Long id, DataFetchingEnvironment env){
        HttpServletRequest request = HttpTool.getHttpServletRequest(env);
        System.out.println(request);
        return authorService.findById(id, env);
    }

    public List<Author> listAuthor(DataFetchingEnvironment env){
        return authorService.findAll(env);
    }
    
    public Page<Author> pageAuthor(Integer page,Integer size,DataFetchingEnvironment env){
        return authorService.findAll(page, size, env);
    }

    public Author findByFirstName(String firstName,DataFetchingEnvironment env){
        return authorService.findByFirstName(firstName, env);
    }
    
}