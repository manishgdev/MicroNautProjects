package com.manish.test;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import io.micronaut.http.client.HttpClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
class HelloWorldTest {

    @Inject
    @Client("/")
    private HttpClient client;

    @Test
    public void test1() {
        var response = client.toBlocking().retrieve("/hello");
        assertEquals("Hello World", response);
    }

    @Test
    public void test2() {
        var response = client.toBlocking().exchange("/hello", String.class);
        assertEquals("Hello World", response.body());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    public void test3() {
        var response = client.toBlocking().retrieve("/hello/field-inject");
        assertEquals("Hello from service!", response);
    }

    @Test
    public void test4() {
        var response = client.toBlocking().exchange("/hello/field-inject", String.class);
        assertEquals("Hello from service!", response.body());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    public void test5() {
        var response = client.toBlocking().retrieve("/hello/constr-inject");
        assertEquals("Hello from service!", response);
    }

    @Test
    public void test6() {
        var response = client.toBlocking().exchange("/hello/constr-inject", String.class);
        assertEquals("Hello from service!", response.body());
        assertEquals(HttpStatus.OK, response.getStatus());
    }
}
