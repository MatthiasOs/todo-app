package de.ossi.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoAppApplication {
    //DO NOT remove "public" -> spring-boot-maven-plugin requires this method signature to be exactly this way
    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }
}