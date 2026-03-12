package com.example.task2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

@SpringBootApplication
public class Task2Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Task2Application.class, args);
    }

    @Override
    public void run(String... args) {
        runXmlContainer();
        runJavaContainer();
    }

    private void runXmlContainer() {
        System.out.println("\n=== Task 2: XML Container ===");
        try (GenericXmlApplicationContext context = new GenericXmlApplicationContext("task2-context.xml")) {
            AccountServiceImpl accountService = context.getBean("accountServiceImpl", AccountServiceImpl.class);
            accountService.save("Hoda");
            accountService.getSavePerson("Hoda");
        }
    }

    private void runJavaContainer() {
        System.out.println("\n=== Task 2: Java Config Container ===");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Task2JavaConfig.class)) {
            AccountServiceImpl accountService = context.getBean("accountServiceImpl", AccountServiceImpl.class);
            accountService.save("Mahmoud");
            accountService.getSavePerson("Mahmoud");
        }
    }
}
