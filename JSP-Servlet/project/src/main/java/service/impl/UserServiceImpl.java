package service.impl;

import config.DatabaseConfig;
import model.User;
import service.UserService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;
import java.util.Optional;

public class UserServiceImpl implements UserService {
	
	@Override
	public Optional<User> findByEmail(String email) {
		String sql = "SELECT * FROM USERS WHERE LOWER(email) = LOWER(?)";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return Optional.of(mapUser(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	@Override
	public Boolean createUser(User user) {
		if (findByEmail(user.getEmail()).isPresent()) {
			return false;
		}
		String sql = "INSERT INTO USERS (name, email, password) VALUES (?, ?, ?)";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getEmail().toLowerCase());
			stmt.setString(3, hashPassword(user.getPassword()));
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Optional<User> authenticate(String email, String password) {
		return findByEmail(email)
			.filter(u -> hashPassword(password).equals(u.getPassword()));
	}
	
	@Override
	public String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes());
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	private User mapUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		return user;
	}
}