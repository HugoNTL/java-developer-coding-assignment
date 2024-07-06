package com.assignment.BooKCRUDApplication.service.impl;

import com.assignment.BooKCRUDApplication.dto.BookDto;
import com.assignment.BooKCRUDApplication.exception.BookNotFoundException;
import com.assignment.BooKCRUDApplication.repository.BookRepository;
import com.assignment.BooKCRUDApplication.resource.Book;
import com.assignment.BooKCRUDApplication.service.BookService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private static final Validator validator = factory.getValidator();

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> getBookByTitleAndAuthor(String author, Boolean published) {
        if (StringUtils.isNotBlank(author) && published != null){
            return bookRepository.findByAuthorAndPublished(author, published);
        }
        if (StringUtils.isNotBlank(author)){
            return bookRepository.findByAuthor(author);
        }
        if (published != null){
            return bookRepository.findByPublished(published);
        }
        return bookRepository.findAll();
    }

    @Override
    public void createBook(BookDto bookDto) {
        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setPublished(bookDto.isPublished());

        Set<ConstraintViolation<Book>> constraintViolations = validator.validate(book);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException("Book not found!")
        );
        bookRepository.delete(book);
    }
}
