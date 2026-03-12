package com.example.task3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Task3JavaConfig {

    @Bean(initMethod = "initPersonService", destroyMethod = "destroyPersonService")
    @Scope("prototype")
    public PersonService personService() {
        return new PersonService();
    }
}
