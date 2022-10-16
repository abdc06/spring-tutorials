package com.example.springrestservice.web;

import com.example.springrestservice.config.BookModelAssembler;
import com.example.springrestservice.entity.Book;
import com.example.springrestservice.exception.BookNotFoundException;
import com.example.springrestservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RequestMapping("/hateoas")
@RestController
public class HateoasBookController {

    final BookRepository repository;

    final BookModelAssembler assembler;

    @GetMapping("/books")
    public CollectionModel<EntityModel<Book>> all() {
        List<EntityModel<Book>> employees = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(employees, linkTo(methodOn(HateoasBookController.class).all()).withSelfRel());
//        List<EntityModel<Book>> employees = repository.findAll().stream()
//                .map(book -> EntityModel.of(book,
//                        linkTo(methodOn(HateoasBookController.class).one(book.getNo())).withSelfRel(),
//                        linkTo(methodOn(HateoasBookController.class).all()).withRel("books")))
//                .collect(Collectors.toList());
//
//        return CollectionModel.of(employees, linkTo(methodOn(HateoasBookController.class).all()).withSelfRel());
    }

    @GetMapping("/books/{no}")
    public EntityModel<Book> one(@PathVariable Long no) {
        Book book = repository.findById(no)
                .orElseThrow(() -> new BookNotFoundException());

        return assembler.toModel(book);
//        return EntityModel.of(book, //
//                linkTo(methodOn(HateoasBookController.class).one(no)).withSelfRel(),
//                linkTo(methodOn(HateoasBookController.class).all()).withRel("books"));
    }
}
