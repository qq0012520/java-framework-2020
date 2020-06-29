package com.tudog.graphqldemo01.api.author;

import java.util.List;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;

@Component
public class AuthorQuery implements GraphQLQueryResolver{
    @Autowired
    private AuthorRepository authorRepository;

    public Author authorById(Long id, DataFetchingEnvironment env){
        DataFetchingFieldSelectionSet selectionSet = env.getSelectionSet();
        if(selectionSet.contains("books")){
            return authorRepository.getById(id);
        }else{
            return authorRepository.findById(id).orElse(null);
        }
    }

    public List<Author> listAuthor(DataFetchingEnvironment env){
        DataFetchingFieldSelectionSet selectionSet = env.getSelectionSet();
        if(selectionSet.contains("books")){
            return authorRepository.getAll();
        }else{
            return authorRepository.findAll();
        }
    }

    public Page<Author> pageAuthor(Integer page,Integer size,DataFetchingEnvironment env){
        DataFetchingFieldSelectionSet selectionSet = env.getSelectionSet();
        if(selectionSet.contains("books")){
            return authorRepository.getAll(PageRequest.of(page, size));
        }else{
            return authorRepository.findAll(PageRequest.of(page, size));
        }
    }
    
}