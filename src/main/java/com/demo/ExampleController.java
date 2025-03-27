package com.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class ExampleController {
	 @GetMapping("/hello")
	 @Operation(summary = "Get all books", description = "Retrieves a list of all books")
	    public String sayHello() {
	        return "Hello, World!";
	    }
}
