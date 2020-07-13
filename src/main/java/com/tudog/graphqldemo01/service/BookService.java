package com.tudog.graphqldemo01.service;

import com.tudog.graphqldemo01.entity.Author;
import com.tudog.graphqldemo01.entity.Book;
import com.tudog.graphqldemo01.repository.AuthorRepository;
import com.tudog.graphqldemo01.tools.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService extends BaseService<Book,Long>{
    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
	public Book addBook(Book book) {
        Author author = authorRepository.findById(book.getAuthorId()).get();
        book.setAuthor(author);
        return this.save(book);
	}
    
}