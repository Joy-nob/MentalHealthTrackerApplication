package com.tracker;

import com.tracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Ensure BCryptPasswordEncoder is used
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
@Transactional
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Password Encoder

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	// Register a new user
	public void registerUser(String username, String password, String email) throws Exception {
		if (userRepository.findByUsername(username) != null) {
			throw new IllegalArgumentException("Username already exists");
		}

		if (userRepository.findByEmail(email) != null) {
			throw new IllegalArgumentException("Email already exists");
		}

		// Hash password and create a new user
		String hashedPassword = passwordEncoder.encode(password);
		User user = new User(username, hashedPassword, email);
		userRepository.save(user); // `createdAt` is set by the `User` entity
	}

	// Login user
	public User loginUser(String username, String password) throws Exception {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new Exception("Username not found");
		}

		// Check password hash using BCrypt
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new Exception("Invalid password");
		}

		return user;
	}
}
