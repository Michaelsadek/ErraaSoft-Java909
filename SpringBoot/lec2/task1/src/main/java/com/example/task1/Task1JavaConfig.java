package com.example.task1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Task1JavaConfig {

    @Bean
    public PersonService personService() {
        return new PersonService();
    }

    @Bean
    public ManagerService managerService() {
        return new ManagerService();
    }
}
