package com.example.LojaModerna.Controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.LojaModerna.Services.UserService;
import com.example.LojaModerna.models.User;

@RestController
@RequestMapping("/users")
@Validated
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register/{id}")
    public ResponseEntity<User> findById(@PathVariable String id){
        User obj = this.userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Validated @RequestBody User obj) {
        User createdUser = userService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
