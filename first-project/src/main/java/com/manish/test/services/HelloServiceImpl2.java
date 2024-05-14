package com.manish.test.services;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named("SecondImpl")
public class HelloServiceImpl2 implements IHelloService{
    @Override
    public String getGreetings() {
        return "Hello from second interface implementation";
    }
}
