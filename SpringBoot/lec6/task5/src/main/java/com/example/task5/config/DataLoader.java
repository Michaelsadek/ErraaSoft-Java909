package com.example.task5.config;

import com.example.task5.entity.AdminUser;
import com.example.task5.entity.Friend;
import com.example.task5.entity.Post;
import com.example.task5.entity.RegularUser;
import com.example.task5.entity.User;
import com.example.task5.entity.UserDetails;
import com.example.task5.repository.FriendRepository;
import com.example.task5.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    public DataLoader(UserRepository userRepository, FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Friend sharedFriend = friendRepository.save(new Friend("Mona"));

        AdminUser adminUser = new AdminUser("Ali", 30, "SUPER");
        adminUser.setUserDetails(new UserDetails("Cairo", "01000000000"));
        adminUser.addFriend(sharedFriend);
        adminUser.addPost(new Post("Admin Post", "Admin user saved with inheritance"));

        RegularUser regularUser = new RegularUser("Sara", 22, "GOLD");
        regularUser.setUserDetails(new UserDetails("Alexandria", "01111111111"));
        regularUser.addFriend(sharedFriend);
        regularUser.addPost(new Post("Regular Post", "Regular user saved with inheritance"));

        userRepository.save(adminUser);
        userRepository.save(regularUser);

        System.out.println("\n=== Hibernate Inheritance Example ===");
        for (User user : userRepository.findAll()) {
            System.out.println("Type: " + user.getClass().getSimpleName()
                    + ", Name: " + user.getName()
                    + ", Address: " + user.getUserDetails().getAddress());
        }
    }
}
