package com.example.Transacoes.infra.controllers;

import com.example.Transacoes.domain.application.usecases.CreateAccount;
import com.example.Transacoes.domain.entities.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController()
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private CreateAccount createAccount;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        try {
            UserApp userApp = this.createAccount.execute(userDto);
            return ResponseEntity.ok().body(userApp);
        } catch (Exception e) {
            HashMap<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}
