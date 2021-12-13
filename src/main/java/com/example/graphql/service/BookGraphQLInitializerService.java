package com.example.graphql.service;

import com.example.graphql.model.Book;
import com.example.graphql.repository.BookRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

@Component
public class BookGraphQLInitializerService {

    private final BookRepository repository;

    public BookGraphQLInitializerService(BookRepository repository) {
        super();
        this.repository = repository;
    }

    @PostConstruct
    private void loadDataIntoHS() {
        Stream.of(
                new Book("123", "Book of Clouds", "Kindle Edition", authors("Chloe Aridis"), "Nov 2017"),
                new Book("124", "Cloud Architecture & Engineering", "Orielly", authors("Peter", "Sam"), "Jan 2015"),
                new Book("123", "Java 9 Programming", "Orielly", authors( "Venkat", "Ram"), "Nov 2016")
        ).forEach(book -> {
            repository.save(book);
        });
    }

    private String[] authors(String... authors) {
        return authors;
    }
}
