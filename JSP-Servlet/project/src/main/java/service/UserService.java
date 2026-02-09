package service;

import model.User;
import java.util.Optional;

public interface UserService {
	Optional<User> findByEmail(String email);
	Boolean createUser(User user);
	Optional<User> authenticate(String email, String password);
	String hashPassword(String password);
}