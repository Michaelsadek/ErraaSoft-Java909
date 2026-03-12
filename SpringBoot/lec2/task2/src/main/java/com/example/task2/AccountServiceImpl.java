package com.example.task2;

public class AccountServiceImpl implements AccountService, UserService {

    private final PersonService personService;

    public AccountServiceImpl(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void save(String name) {
        System.out.println("AccountServiceImpl.save delegates to PersonService");
        personService.save(name);
    }

    @Override
    public void getSavePerson(String name) {
        System.out.println("AccountServiceImpl.getSavePerson -> " + name);
        personService.save(name);
    }
}
