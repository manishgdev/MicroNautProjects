package com.manish.mn.broker;

import com.fasterxml.jackson.databind.JsonNode;
import com.manish.mn.broker.data.InMemoryStore;
import com.manish.mn.broker.model.Symbol;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class SymbolsControllerTest {

    @Inject
    @Client("/symbols")
    HttpClient client;

    @Inject
    InMemoryStore inMemoryStore;

    @BeforeEach
    void setup() {
        inMemoryStore.initializeWith(10);
    }

    @Test
    void test1() {
        var response = client.toBlocking().exchange("/", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(10, response.getBody().get().size());
    }

    @Test
    void test2() {
        var symbol = new Symbol("BSE");
        inMemoryStore.getSymbols().put(symbol.value(), symbol);

        var response = client.toBlocking().exchange("/" + symbol.value(), Symbol.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(symbol, response.getBody().get());

    }

    @Test
    void test3() {
        var response = client.toBlocking().exchange("/filter?max=10", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(10, response.getBody().get().size());
    }

    @Test
    void test4() {
        var response = client.toBlocking().exchange("/filter?offset=2&max=10", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(8, response.getBody().get().size());
    }

    @Test
    void test5() {
        var response = client.toBlocking().exchange("/filter?offset=4", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(6, response.getBody().get().size());
    }

}
