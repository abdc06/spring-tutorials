package com.example.springrestservice.web;

import com.example.springrestservice.entity.Book;
import com.example.springrestservice.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    WebApplicationContext ctx;

    @Autowired
    ObjectMapper objectMapper;
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BookRepository repository;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void all() throws Exception {
        List<Book> all = repository.findAll();
        String allAsString = objectMapper.writeValueAsString(all);

        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(allAsString));
    }

    @Test
    void one() throws Exception {
        Optional<Book> byId = repository.findById(3L);
        String oneAsString = objectMapper.writeValueAsString(byId);

        mockMvc.perform(get("/books/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(oneAsString));

        mockMvc.perform(get("/books/4"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find book"));
    }

    @Test
    void add() throws Exception {
        Book book = new Book("하얼빈", "김훈");
        String bookAsString = objectMapper.writeValueAsString(book);

        assertThat(book.getNo()).isNull();

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAsString))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void changeBook() throws Exception {
        Book book = new Book("강릉대첩", "최재효");
        book.setNo(3L);
        String bookAsString = objectMapper.writeValueAsString(book);

        mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookAsString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(bookAsString));

        Book newBook = new Book(10L,"꽃들의 암투", "최재효");
        String newBookAsString = objectMapper.writeValueAsString(newBook);

        mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBookAsString))
                .andDo(print())
                .andExpect(status().isOk());
    }
}