package com.example.task1;

public class PersonService implements UserService {

    @Override
    public void save(String name) {
        System.out.println("PersonService.save -> " + name);
    }

    @Override
    public void update(String name) {
        System.out.println("PersonService.update -> " + name);
    }
}
