package com.example.springrestservice.config;

import com.example.springrestservice.entity.Book;
import com.example.springrestservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class DataConfig {

    private static final Logger log = LoggerFactory.getLogger(DataConfig.class);

    final BookRepository repository;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            log.info("Preloading " + repository.save(new Book("금요일엔 시골집으로 퇴근합니다 평범한 직장인 시골에 집을 짓다 자기만의 방", "김미리")));
            log.info("Preloading " + repository.save(new Book("아빠와 함께 산책", "볼프 에를브루흐")));
            log.info("Preloading " + repository.save(new Book("달 샤베트", "백희나")));
        };
    }
}
