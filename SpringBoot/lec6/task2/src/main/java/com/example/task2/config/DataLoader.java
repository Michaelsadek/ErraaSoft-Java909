package com.example.task2.config;

import com.example.task2.entity.Friend;
import com.example.task2.entity.Post;
import com.example.task2.entity.User;
import com.example.task2.entity.UserDetails;
import com.example.task2.repository.FriendRepository;
import com.example.task2.repository.PostRepository;
import com.example.task2.repository.UserDetailsRepository;
import com.example.task2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final FriendRepository friendRepository;
    private final PostRepository postRepository;

    public DataLoader(UserRepository userRepository,
                      UserDetailsRepository userDetailsRepository,
                      FriendRepository friendRepository,
                      PostRepository postRepository) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.friendRepository = friendRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        addUserWithUserDetails();
        addUserWithFriends();
        addUserWithPost();
    }

    private void addUserWithUserDetails() {
        UserDetails userDetails = new UserDetails("Cairo", "01000000000");
        userDetailsRepository.save(userDetails);

        User user = new User("Ali", 22);
        user.setUserDetails(userDetails);
        userRepository.save(user);

        System.out.println("Saved user with details: " + user.getName());
    }

    private void addUserWithFriends() {
        User user = new User("Sara", 24);
        userRepository.save(user);

        Friend friendOne = new Friend("Mona");
        Friend friendTwo = new Friend("Omar");
        friendRepository.save(friendOne);
        friendRepository.save(friendTwo);

        user.addFriend(friendOne);
        user.addFriend(friendTwo);
        userRepository.save(user);

        System.out.println("Saved user with friends: " + user.getName());
    }

    private void addUserWithPost() {
        User user = new User("Khaled", 28);
        userRepository.save(user);

        Post post = new Post("Spring JPA", "Post saved without cascade");
        user.addPost(post);
        postRepository.save(post);

        System.out.println("Saved user with post: " + user.getName());
    }
}
