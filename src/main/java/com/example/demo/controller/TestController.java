package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController adalah REST controller sederhana untuk menangani permintaan HTTP.
 *
 * <p>Controller ini menyediakan endpoint dasar untuk menguji aplikasi Spring Boot.
 *
 * @author Harist Islami
 * @version 1.0
 * @since 2025-09-20
 */
@RestController
public class TestController {

    /**
     * Endpoint GET default yang mengembalikan pesan "Hello World".
     *
     * @return String "Hello World" sebagai respons
     */
    @GetMapping
    public String index() {
        return "Hello World";
    }
}
