package com.tudog.graphqldemo01.api.author;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;

@Component
public class AuthorQuery implements GraphQLQueryResolver{
    @Autowired
    private AuthorRepository authorRepository;

    @Transactional(readOnly=true)
    public Author authorById(Long id, DataFetchingEnvironment env){
        DataFetchingFieldSelectionSet selectionSet = env.getSelectionSet();
        if(selectionSet.contains("books")){
            return authorRepository.getById(id);
        }else{
            return authorRepository.findById(id).orElse(null);
        }
    }
}