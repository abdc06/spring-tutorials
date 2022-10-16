package com.example.springrestservice.web;

import com.example.springrestservice.entity.Book;
import com.example.springrestservice.exception.BookNotFoundException;
import com.example.springrestservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class BookController {

    final BookRepository repository;

    @GetMapping("/books")
    public List<Book> all() {
        return repository.findAll();
    }

    @GetMapping("/books/{no}")
    public Book one(@PathVariable Long no) {
        return repository.findById(no)
                .orElseThrow(BookNotFoundException::new);

    }

    @PostMapping("/books")
    public Book add(@RequestBody Book book) {
        return repository.save(book);
    }

    @PutMapping("/books")
    public Book changeBook(@RequestBody Book book) {
        return repository.findById(book.getNo())
                .map(b -> {
                    b.setTitle(book.getTitle());
                    b.setWriter(book.getWriter());
                    return repository.save(b);
                }).orElseGet(() -> {
                    return repository.save(book);
                });
    }

    @DeleteMapping("/books/{no}")
    public void remove(@PathVariable Long no) {
        repository.deleteById(no);
    }
}
