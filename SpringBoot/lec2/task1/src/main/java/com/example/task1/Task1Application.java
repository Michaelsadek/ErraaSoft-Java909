package com.example.task1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

@SpringBootApplication
public class Task1Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Task1Application.class, args);
    }

    @Override
    public void run(String... args) {
        runXmlContainer();
        runJavaContainer();
    }

    private void runXmlContainer() {
        System.out.println("\n=== Task 1: XML Container ===");
        try (GenericXmlApplicationContext context = new GenericXmlApplicationContext("task1-context.xml")) {
            PersonService personService = context.getBean("personService", PersonService.class);
            ManagerService managerService = context.getBean("managerService", ManagerService.class);

            personService.save("Ali");
            personService.update("Ali");
            managerService.save("Sara");
            managerService.update("Sara");
        }
    }

    private void runJavaContainer() {
        System.out.println("\n=== Task 1: Java Config Container ===");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Task1JavaConfig.class)) {
            PersonService personService = context.getBean("personService", PersonService.class);
            ManagerService managerService = context.getBean("managerService", ManagerService.class);

            personService.save("Omar");
            personService.update("Omar");
            managerService.save("Mona");
            managerService.update("Mona");
        }
    }
}
