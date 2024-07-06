package com.assignment.BooKCRUDApplication.repository;

import com.assignment.BooKCRUDApplication.resource.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorAndPublished(String author, boolean published);

    List<Book> findByAuthor(String author);

    List<Book> findByPublished(boolean published);
}
