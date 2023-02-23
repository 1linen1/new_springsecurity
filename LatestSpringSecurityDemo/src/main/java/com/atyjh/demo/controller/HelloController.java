package com.atyjh.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/demo")
@RestController
public class HelloController {

    @PreAuthorize("hasAuthority('test222')")
    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring Security";
    }

    @PreAuthorize("hasAuthority('dev:codePull')")
    @GetMapping("/goodbye")
    public String goodbye() {
        return "Hello Spring Security";
    }

}
