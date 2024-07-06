package com.assignment.BooKCRUDApplication.repository;

import com.assignment.BooKCRUDApplication.resource.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindByAuthor() {
        //given
        Book book1 = Book.builder()
                .title("Book1")
                .author("Alice")
                .published(true)
                .build();

        bookRepository.save(book1);

        //when
        List<Book> books = bookRepository.findByAuthor("Alice");
        List<Book> bookNotExists = bookRepository.findByAuthor("Bob");

        //then
        Assertions.assertThat(books).isNotNull();
        Assertions.assertThat(books.size()).isEqualTo(1);

        Assertions.assertThat(bookNotExists).isNotNull();
        Assertions.assertThat(bookNotExists.size()).isEqualTo(0);
    }


    @Test
    public void testFindByPublished() {
        //given
        Book book1 = Book.builder()
                .title("Book1")
                .author("Alice")
                .published(true)
                .build();

        bookRepository.save(book1);

        //when
        List<Book> books = bookRepository.findByPublished(true);
        List<Book> booksNotPublished = bookRepository.findByPublished(false);

        //then
        Assertions.assertThat(books).isNotNull();
        Assertions.assertThat(books.size()).isEqualTo(1);
        Assertions.assertThat(booksNotPublished).isNotNull();
        Assertions.assertThat(booksNotPublished.size()).isEqualTo(0);
    }


    @Test
    public void testFindByAuthorAndPublished() {
        //given
        Book book1 = Book.builder()
                .title("Book1")
                .author("Alice")
                .published(true)
                .build();

        bookRepository.save(book1);

        //when
        List<Book> books = bookRepository.findByAuthorAndPublished("Alice", true);
        List<Book> booksNotPublished = bookRepository.findByAuthorAndPublished("Alice", false);
        List<Book> booksNoAuthor = bookRepository.findByAuthorAndPublished("Bob", true);
        //then
        Assertions.assertThat(books).isNotNull();
        Assertions.assertThat(books.size()).isEqualTo(1);
        Assertions.assertThat(booksNotPublished).isNotNull();
        Assertions.assertThat(booksNotPublished.size()).isEqualTo(0);
        Assertions.assertThat(booksNoAuthor).isNotNull();
        Assertions.assertThat(booksNoAuthor.size()).isEqualTo(0);
    }
}
