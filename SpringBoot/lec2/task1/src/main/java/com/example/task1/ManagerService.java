package com.example.task1;

public class ManagerService implements UserService {

    @Override
    public void save(String name) {
        System.out.println("ManagerService.save -> " + name);
    }

    @Override
    public void update(String name) {
        System.out.println("ManagerService.update -> " + name);
    }
}
