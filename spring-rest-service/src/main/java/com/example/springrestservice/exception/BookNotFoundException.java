package com.example.springrestservice.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super("Could not find book");
    }
}
