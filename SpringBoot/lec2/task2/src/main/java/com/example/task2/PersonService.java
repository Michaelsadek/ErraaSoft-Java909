package com.example.task2;

public class PersonService implements UserService {

    @Override
    public void save(String name) {
        System.out.println("PersonService.save -> " + name);
    }
}
