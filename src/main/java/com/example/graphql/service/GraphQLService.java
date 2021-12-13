package com.example.graphql.service;

import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class GraphQLService {

    @Value("classpath:books.graphql")
    private Resource resource;

    private DataFetcher allBooksDataFetcher;

    private DataFetcher bookDataFetcher;

    public GraphQLService(DataFetcher allBooksDataFetcher, DataFetcher bookDataFetcher) {
        super();
        this.allBooksDataFetcher = allBooksDataFetcher;
        this.bookDataFetcher = bookDataFetcher;
    }

    @Bean
    public GraphQL graphQL() throws IOException {
        // get the schema
        File schemaFile = resource.getFile();
        GraphQLSchema schema = parseSchema(schemaFile);

        return GraphQL.newGraphQL(schema).build();
    }

    private GraphQLSchema parseSchema(File schemaFile) {
        //parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = this.buildRuntimeWiring();

        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        return schema;
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring ->
                    typeWiring
                            .dataFetcher("allBooks", allBooksDataFetcher)
                            .dataFetcher("book", bookDataFetcher))
                .build();
    }
}
