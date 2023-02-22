package com.atyjh.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LatestSpringSecurityDemoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(LatestSpringSecurityDemoApplication.class, args);
        System.out.println("通过debug查看run中的过滤器链");
    }

}
