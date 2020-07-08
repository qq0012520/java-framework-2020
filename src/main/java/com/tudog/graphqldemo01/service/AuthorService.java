package com.tudog.graphqldemo01.service;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.repository.AuthorRepository;
import com.tudog.graphqldemo01.tools.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import graphql.schema.DataFetchingEnvironment;

@Service
public class AuthorService extends BaseService<Author,Long>{
    @Autowired
    private AuthorRepository authorRepository;

    public Author findByFirstName(String firstName,DataFetchingEnvironment env){
        return authorRepository.findByFirstName(firstName, extractedGraphAttributes(env));
    }
}