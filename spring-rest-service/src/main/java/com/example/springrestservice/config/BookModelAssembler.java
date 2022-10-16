package com.example.springrestservice.config;

import com.example.springrestservice.entity.Book;
import com.example.springrestservice.web.HateoasBookController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<Book>> {

    @Override
    public EntityModel<Book> toModel(Book entity) {
        return EntityModel.of(entity, //
                linkTo(methodOn(HateoasBookController.class).one(entity.getNo())).withSelfRel(),
                linkTo(methodOn(HateoasBookController.class).all()).withRel("employees"));
    }

}
