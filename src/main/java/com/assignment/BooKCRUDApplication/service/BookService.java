package com.assignment.BooKCRUDApplication.service;

import com.assignment.BooKCRUDApplication.dto.BookDto;
import com.assignment.BooKCRUDApplication.resource.Book;

import java.util.List;

public interface BookService {

    List<Book> getBookByTitleAndAuthor(String author, Boolean published);

    void createBook(BookDto bookDto);

    void deleteBook(Long id);
}
