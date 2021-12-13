package com.example.graphql.resource;

import com.example.graphql.service.GraphQLService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/books")
@RestController
public class BookResource {

    private final GraphQL graphQL;

    public BookResource(GraphQL graphQL) {
        super();
        this.graphQL = graphQL;
    }

    @PostMapping
    public ResponseEntity<Object> getAllBooks(@RequestBody String query) {
        ExecutionResult execute = this.graphQL.execute(query);

        return new ResponseEntity<Object>(execute, HttpStatus.OK);
    }

}
