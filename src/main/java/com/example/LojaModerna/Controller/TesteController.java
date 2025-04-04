package com.example.LojaModerna.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TesteController {
    @GetMapping("/")
    public String hello() {
        return "API funcionando!";
    }
}
