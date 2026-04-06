package com.example.task4.config;

import com.example.task4.eager.entity.EagerUser;
import com.example.task4.eager.entity.EagerUserDetails;
import com.example.task4.eager.repository.EagerUserRepository;
import com.example.task4.lazy.entity.LazyUser;
import com.example.task4.lazy.entity.LazyUserDetails;
import com.example.task4.lazy.repository.LazyUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnitUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements CommandLineRunner {

    private final LazyUserRepository lazyUserRepository;
    private final EagerUserRepository eagerUserRepository;
    private final EntityManager entityManager;
    private final PersistenceUnitUtil persistenceUnitUtil;

    public DataLoader(LazyUserRepository lazyUserRepository,
                      EagerUserRepository eagerUserRepository,
                      EntityManager entityManager,
                      EntityManagerFactory entityManagerFactory) {
        this.lazyUserRepository = lazyUserRepository;
        this.eagerUserRepository = eagerUserRepository;
        this.entityManager = entityManager;
        this.persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();
    }

    @Override
    @Transactional
    public void run(String... args) {
        showLazyFetchExample();
        showEagerFetchExample();
    }

    private void showLazyFetchExample() {
        System.out.println("\n=== LAZY Fetch Example ===");

        LazyUser user = new LazyUser("Ali", 22);
        user.setUserDetails(new LazyUserDetails("Cairo", "01000000000"));
        lazyUserRepository.save(user);

        entityManager.flush();
        entityManager.clear();

        LazyUser fetchedUser = lazyUserRepository.findById(user.getId()).orElseThrow();

        System.out.println("User name: " + fetchedUser.getName());
        System.out.println("userDetails loaded before access: "
                + persistenceUnitUtil.isLoaded(fetchedUser, "userDetails"));
        System.out.println("Address: " + fetchedUser.getUserDetails().getAddress());
        System.out.println("Phone: " + fetchedUser.getUserDetails().getPhone());
        System.out.println("userDetails loaded after access: "
                + persistenceUnitUtil.isLoaded(fetchedUser, "userDetails"));
    }

    private void showEagerFetchExample() {
        System.out.println("\n=== EAGER Fetch Example ===");

        EagerUser user = new EagerUser("Sara", 24);
        user.setUserDetails(new EagerUserDetails("Alexandria", "01111111111"));
        eagerUserRepository.save(user);

        entityManager.flush();
        entityManager.clear();

        EagerUser fetchedUser = eagerUserRepository.findById(user.getId()).orElseThrow();

        System.out.println("User name: " + fetchedUser.getName());
        System.out.println("userDetails loaded before access: "
                + persistenceUnitUtil.isLoaded(fetchedUser, "userDetails"));
        System.out.println("Address: " + fetchedUser.getUserDetails().getAddress());
        System.out.println("Phone: " + fetchedUser.getUserDetails().getPhone());
        System.out.println("userDetails loaded after access: "
                + persistenceUnitUtil.isLoaded(fetchedUser, "userDetails"));
    }
}
