package com.assignment.BooKCRUDApplication.service;

import com.assignment.BooKCRUDApplication.dto.BookDto;
import com.assignment.BooKCRUDApplication.exception.BookNotFoundException;
import com.assignment.BooKCRUDApplication.repository.BookRepository;
import com.assignment.BooKCRUDApplication.resource.Book;
import com.assignment.BooKCRUDApplication.service.impl.BookServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testCreateBook() {
        //given
        BookDto bookDto1 = BookDto.builder()
                .title("Book1")
                .author("Alice")
                .published(true)
                .build();

        Book book1 = Book.builder()
                .title("Book1")
                .author("A*")
                .published(true)
                .build();

        when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book1);

        //when

        //then
        assertAll(
                () -> bookService.createBook(bookDto1)
        );
    }

    @Test
    public void testCreateInvalidBookAuthor() throws ConstraintViolationException {
        //given
        BookDto bookDto1 = BookDto.builder()
                .title("Book1")
                .author("A*")
                .published(true)
                .build();

        //when

        //then
        assertThrows(
                ConstraintViolationException.class,
                () -> bookService.createBook(bookDto1)
        );
    }

    @Test
    public void testCreateInvalidBookTitle() throws ConstraintViolationException {
        //given
        BookDto bookDto1 = BookDto.builder()
                .title("Book*")
                .author("Alice")
                .published(true)
                .build();

        //when

        //then
        assertThrows(
                ConstraintViolationException.class,
                () -> bookService.createBook(bookDto1)
        );

    }

    @Test
    public void testDeleteBook() {
        //given
        Long id = 1L;
        Book book1 = Book.builder()
                .id(id)
                .title("Book1")
                .author("Alice")
                .published(true)
                .build();

        //when
        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book1));
        doNothing().when(bookRepository).delete(book1);

        //then
        assertAll(
                () -> bookService.deleteBook(id)
        );

    }

    @Test
    public void testDeleteBookNotExist() {
        //given

        //when

        //then
        assertThrows(
                BookNotFoundException.class,
                () -> bookService.deleteBook(1L)
        );

    }

    @Test
    public void testGetBookByTitleAndAuthor() {
        //given
        String title = "Book1";
        String author = "Alice";

        Book book1 = Book.builder()
                .title(title)
                .author(author)
                .published(true)
                .build();

        //when
        when(bookRepository.findByAuthorAndPublished(author, true)).thenReturn(Collections.singletonList(book1));
        when(bookRepository.findByAuthor(author)).thenReturn(Collections.singletonList(book1));
        when(bookRepository.findByPublished(true)).thenReturn(Collections.singletonList(book1));
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book1));

        List<Book> books = bookService.getBookByTitleAndAuthor(author, true);
        List<Book> booksWithAuthor = bookService.getBookByTitleAndAuthor(author, null);
        List<Book> booksWithPublished = bookService.getBookByTitleAndAuthor(null, true);
        List<Book> booksAll = bookService.getBookByTitleAndAuthor(null, null);

        //then
        Assertions.assertThat(books).isNotNull();
        Assertions.assertThat(books.size()).isEqualTo(1);

        Assertions.assertThat(booksWithAuthor).isNotNull();
        Assertions.assertThat(booksWithAuthor.size()).isEqualTo(1);

        Assertions.assertThat(booksWithPublished).isNotNull();
        Assertions.assertThat(booksWithPublished.size()).isEqualTo(1);

        Assertions.assertThat(booksAll).isNotNull();
        Assertions.assertThat(booksAll.size()).isEqualTo(1);
    }
}
