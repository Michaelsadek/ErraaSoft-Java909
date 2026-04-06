package com.example.task3.config;

import com.example.task3.entity.Friend;
import com.example.task3.entity.Post;
import com.example.task3.entity.User;
import com.example.task3.entity.UserDetails;
import com.example.task3.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        addUserWithUserDetails();
        addUserWithFriends();
        addUserWithPost();
    }

    private void addUserWithUserDetails() {
        User user = new User("Ali", 22);
        user.setUserDetails(new UserDetails("Cairo", "01000000000"));
        userRepository.save(user);

        System.out.println("Saved user with details using cascade: " + user.getName());
    }

    private void addUserWithFriends() {
        User user = new User("Sara", 24);
        user.addFriend(new Friend("Mona"));
        user.addFriend(new Friend("Omar"));
        userRepository.save(user);

        System.out.println("Saved user with friends using cascade: " + user.getName());
    }

    private void addUserWithPost() {
        User user = new User("Khaled", 28);
        user.addPost(new Post("Spring JPA", "Post saved using cascade"));
        userRepository.save(user);

        System.out.println("Saved user with post using cascade: " + user.getName());
    }
}
