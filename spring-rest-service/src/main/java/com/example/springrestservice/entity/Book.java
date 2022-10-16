package com.example.springrestservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
@Entity
public class Book {

    @Id @GeneratedValue
    private Long no;

    private String title;

    private String writer;

    public Book(String title, String writer) {
        this.title = title;
        this.writer = writer;
    }
}
