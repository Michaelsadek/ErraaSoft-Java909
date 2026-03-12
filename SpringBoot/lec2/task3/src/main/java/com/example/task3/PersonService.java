package com.example.task3;

public class PersonService implements UserService {

    public void initPersonService() {
        System.out.println("PersonService init method called");
    }

    public void destroyPersonService() {
        System.out.println("PersonService destroy method called");
    }

    @Override
    public void save(String name) {
        System.out.println("PersonService.save -> " + name);
    }
}
