package com.manish.test.config;

import io.micronaut.context.annotation.ConfigurationProperties;

import javax.validation.constraints.NotBlank;

@ConfigurationProperties("hello.world.translation")
// Micronaut uses Standard 2.0 Bean Validation framework
public interface IHelloWorlConfig {
    @NotBlank
    String getDe();

    String getEn();

}
