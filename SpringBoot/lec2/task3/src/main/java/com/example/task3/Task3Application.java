package com.example.task3;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

@SpringBootApplication
public class Task3Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Task3Application.class, args);
    }

    @Override
    public void run(String... args) {
        runXmlContainer();
        runJavaContainer();
    }

    private void runXmlContainer() {
        System.out.println("\n=== Task 3: XML Container ===");
        try (GenericXmlApplicationContext context = new GenericXmlApplicationContext("task3-context.xml")) {
            PersonService firstBean = context.getBean("personService", PersonService.class);
            PersonService secondBean = context.getBean("personService", PersonService.class);

            firstBean.save("Prototype XML 1");
            secondBean.save("Prototype XML 2");
            System.out.println("Different prototype instances: "
                    + (System.identityHashCode(firstBean) != System.identityHashCode(secondBean)));

            context.getBeanFactory().destroyBean("personService", firstBean);
            context.getBeanFactory().destroyBean("personService", secondBean);
        }
    }

    private void runJavaContainer() {
        System.out.println("\n=== Task 3: Java Config Container ===");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Task3JavaConfig.class)) {
            PersonService firstBean = context.getBean("personService", PersonService.class);
            PersonService secondBean = context.getBean("personService", PersonService.class);

            firstBean.save("Prototype Java 1");
            secondBean.save("Prototype Java 2");
            System.out.println("Different prototype instances: "
                    + (System.identityHashCode(firstBean) != System.identityHashCode(secondBean)));

            context.getBeanFactory().destroyBean("personService", firstBean);
            context.getBeanFactory().destroyBean("personService", secondBean);
        }
    }
}
