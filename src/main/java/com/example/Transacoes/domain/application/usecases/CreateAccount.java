package com.example.Transacoes.domain.application.usecases;

import com.example.Transacoes.domain.entities.UserApp;
import com.example.Transacoes.infra.controllers.UserDto;
import com.example.Transacoes.infra.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CreateAccount {
    @Autowired
    private UserRepository userRepository;

    public UserApp execute(UserDto userDto) {
        String accountNumber;
        do {
            accountNumber = this.generateAccountNumber();
        } while (userRepository.existsByAccountNumber(accountNumber));
        UserApp userApp = new UserApp(userDto.name(), accountNumber);
        return this.userRepository.save(userApp);
    }

    private String generateAccountNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
