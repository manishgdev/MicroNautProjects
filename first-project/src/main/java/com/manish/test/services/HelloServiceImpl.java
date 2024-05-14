package com.manish.test.services;

import io.micronaut.context.annotation.Primary;
import jakarta.inject.Singleton;

@Primary
@Singleton
public class HelloServiceImpl implements IHelloService{
    @Override
    public String getGreetings() {
        return "Hello from Interface Implementation";
    }
}
