package com.assignment.BooKCRUDApplication.controller;

import com.assignment.BooKCRUDApplication.dto.BookDto;
import com.assignment.BooKCRUDApplication.exception.BookNotFoundException;
import com.assignment.BooKCRUDApplication.resource.Book;
import com.assignment.BooKCRUDApplication.service.BookService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/createBook")
    public ResponseEntity<HttpStatus> createBook(@RequestBody BookDto bookDto) {
        try {
            bookService.createBook(bookDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/readBook")
    public ResponseEntity<List<Book>> readBook(@RequestParam(value="author", required = false) String author,
                                               @RequestParam(value="published", required = false) Boolean published) {
        List<Book> books = bookService.getBookByTitleAndAuthor(author, published);
        if (books.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<HttpStatus> deleteBook(@RequestParam Long id) {
        try {
            bookService.deleteBook(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
