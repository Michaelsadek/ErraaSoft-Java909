package com.example.task2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Task2JavaConfig {

    @Bean
    public PersonService personService() {
        return new PersonService();
    }

    @Bean
    public AccountServiceImpl accountServiceImpl(PersonService personService) {
        return new AccountServiceImpl(personService);
    }
}
