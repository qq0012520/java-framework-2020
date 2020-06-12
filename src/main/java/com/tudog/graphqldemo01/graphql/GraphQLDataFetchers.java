package com.tudog.graphqldemo01.graphql;

import com.google.common.collect.ImmutableMap;
import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.entity.Book;

import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class GraphQLDataFetchers {
        private static List<Book> books = Arrays.asList(
                new Book(1, "Harry Potter and the Philosopher's Stone", 223, 1),
                new Book(2, "Moby Dick", 635, 2),
                new Book(3, "Interview with the vampire", 371, 3)
                );


        private static List<Author> authors = Arrays.asList(
                new Author(1, "Joanne", "Rowling"),
                new Author(2, "Herman", "Melville"),
                new Author(3, "Anne", "Rowling")
        );
    
    
    
        public DataFetcher<Book> getBookByIdDataFetcher() {
            return dataFetchingEnvironment -> {
                Integer bookId = Integer.parseInt(dataFetchingEnvironment.getArgument("id"));
                return books
                        .stream()
                        .filter(book -> book.getId() == bookId)
                        .findFirst()
                        .orElse(null);
            };
        }
    
        public DataFetcher<Author> getAuthorDataFetcher() {
            return dataFetchingEnvironment -> {
                Book book = dataFetchingEnvironment.getSource();
                Integer authorId = book.getAuthorId();
                return authors
                        .stream()
                        .filter(author -> author.getId() == authorId)
                        .findFirst()
                        .orElse(null);
            };
        }

	public DataFetcher<List<Book>> getBooksDataFetcher() {
            return dataFetchingEnvironment -> {
                    return books;
            };
        }	

}