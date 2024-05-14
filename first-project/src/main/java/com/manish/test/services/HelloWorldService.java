package com.manish.test.services;

import jakarta.inject.Singleton;

@Singleton
public class HelloWorldService {

    public String helloService()
    {
        return "Hello from service!";
    }
}
