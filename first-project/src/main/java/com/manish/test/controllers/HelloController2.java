package com.manish.test.controllers;

import com.manish.test.services.IHelloService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import jakarta.inject.Named;


@Controller("/hello2")
public class HelloController2 {
    @Inject
    IHelloService service;

    @Inject
    @Named("SecondImpl")
    IHelloService service2;

    @Get(uri = "/message", produces = MediaType.TEXT_PLAIN)
    String getMessage() {
        return service.getGreetings();
    }

    @Get(uri = "/message2", produces = MediaType.TEXT_PLAIN)
    String getMessage2() {
        return service2.getGreetings();
    }
}
