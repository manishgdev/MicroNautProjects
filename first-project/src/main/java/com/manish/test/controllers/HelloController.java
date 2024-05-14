package com.manish.test.controllers;

import com.manish.test.services.HelloWorldService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/hello")
public class HelloController {

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Inject
    HelloWorldService helloFieldInjection;

    private final HelloWorldService helloConstrInjection;

    public HelloController(HelloWorldService helloConstrInjection) {
        this.helloConstrInjection = helloConstrInjection;
    }

    @Get(produces = MediaType.TEXT_PLAIN)
    public String helloWorld() {
        log.info("Inside default path");
        return "Hello World";
    }

    @Get(uri = "/field-inject", produces = MediaType.TEXT_PLAIN)
    public String helloFromService() {
        log.info("Inside Field Injection");
        return helloFieldInjection.helloService();
    }

    @Get(uri = "/constr-inject", produces = MediaType.TEXT_PLAIN)
    public String helloFromServiceConstr() {
        return helloConstrInjection.helloService();
    }
}
