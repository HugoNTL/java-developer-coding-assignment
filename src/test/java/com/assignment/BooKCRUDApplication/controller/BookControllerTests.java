package com.assignment.BooKCRUDApplication.controller;

import com.assignment.BooKCRUDApplication.dto.BookDto;
import com.assignment.BooKCRUDApplication.exception.BookNotFoundException;
import com.assignment.BooKCRUDApplication.resource.Book;
import com.assignment.BooKCRUDApplication.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testCreateBook() throws Exception {
        //given
        BookDto bookDto1 = BookDto.builder()
                .title("Book1")
                .author("Alice")
                .published(true)
                .build();

        //when
        ResultActions response = mockMvc.perform(post("/createBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto1)));

        //then
        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testCreateBookInvalid() throws Exception {
        //given
        BookDto bookDto1 = BookDto.builder()
                .title("Book1")
                .author("A*")
                .published(true)
                .build();

        doThrow(new ConstraintViolationException(emptySet())).when(bookService).createBook(bookDto1);

        //when
        ResultActions response = mockMvc.perform(post("/createBook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto1)));

        //then
        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testReadBook() throws Exception {
        //given
        String title = "Book1";
        String author = "Alice";

        Book book1 = Book.builder()
                .id(1L)
                .title(title)
                .author(author)
                .published(true)
                .build();

        when(bookService.getBookByTitleAndAuthor(author, true)).thenReturn(Collections.singletonList(book1));

        //when
        ResultActions response = mockMvc.perform(get("/readBook")
                .param("author", author)
                .param("published", "true"));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title", CoreMatchers.is(book1.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author", CoreMatchers.is(book1.getAuthor())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].published", CoreMatchers.is(book1.isPublished())));
    }

    @Test
    public void testReadBookNotFound() throws Exception {
        //given
        when(bookService.getBookByTitleAndAuthor(any(), any())).thenReturn(emptyList());

        //when
        ResultActions response = mockMvc.perform(get("/readBook")
                .param("author", "Alice")
                .param("published", "true"));

        //then
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testDeleteBook() throws Exception {
        //given

        //when
        ResultActions response = mockMvc.perform(delete("/deleteBook")
                .param("id", "1"));

        //then
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteBookNotFound() throws Exception {
        //given
        doThrow(new BookNotFoundException("Book Not Found!")).when(bookService).deleteBook(1L);

        //when
        ResultActions response = mockMvc.perform(delete("/deleteBook")
                .param("id", "1"));

        //then
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
