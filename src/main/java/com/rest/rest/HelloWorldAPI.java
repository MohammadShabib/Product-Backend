package com.rest.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldAPI {
    @RequestMapping
    public String getHelloWorld()
    {
        return "Hello World!";
    }
}
