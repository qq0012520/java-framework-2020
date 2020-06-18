package com.tudog.graphqldemo01.api.book;

import java.util.Arrays;
import java.util.List;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.entity.Book;

import org.springframework.stereotype.Component;

import graphql.kickstart.tools.GraphQLQueryResolver;


@Component
public class BookQuery implements GraphQLQueryResolver{
    public static List<Book> books = Arrays.asList(
        new Book(1, "Harry Potter and the Philosopher's Stone", 223, 1),
        new Book(2, "Moby Dick", 635, 2),
        new Book(3, "Interview with the vampire", 371, 3)
    );

    public static List<Author> authors = Arrays.asList(
            new Author(1, "Joanne", "Rowling"),
            new Author(2, "Herman", "Melville"),
            new Author(3, "Anne", "Rowling")
    );

    public Book bookById(Integer id){
        Book bookResult = books
                        .stream()
                        .filter(book -> book.getId() == id)
                        .findFirst()
                        .orElse(null);
        for (Author author : authors) {
            if(bookResult.getAuthorId() == author.getId()){
                bookResult.setAuthor(author);
            }
        }
        return bookResult;
    }

    // public BookQuery bookQuery(Integer id){
    //     return this;
    // }
}