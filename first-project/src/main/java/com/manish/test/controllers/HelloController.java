package com.manish.test.controllers;

import com.manish.test.config.IHelloWorlConfig;
import com.manish.test.services.HelloWorldService;
import io.micronaut.context.annotation.Property;
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

    @Property(name="hello.world.message2")
    String helloConfig2;  // Config Injection through field (no need to add @Inject annotation)

    private final HelloWorldService helloConstrInjection;

    private final String helloConfig; // Config Injection through constructor

    private final IHelloWorlConfig translationConfig;

    public HelloController(HelloWorldService helloConstrInjection, @Property(name = "hello.world.message") String helloConfig, IHelloWorlConfig translationConfig) {
        this.helloConstrInjection = helloConstrInjection;
        this.helloConfig = helloConfig;
        this.translationConfig = translationConfig;
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

    @Get(uri = "/config", produces = MediaType.TEXT_PLAIN)
    public String helloFromConfig() {
        return helloConfig;
    }

    @Get(uri = "/config2", produces = MediaType.TEXT_PLAIN)
    public String helloFromConfig2() {
        return helloConfig2;
    }

    @Get(uri = "/translation", produces = MediaType.APPLICATION_JSON)
    public IHelloWorlConfig translationConfig() {
        return translationConfig;
    }

}
