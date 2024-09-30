package com.Business.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Business.Entity.User;
import com.Business.Repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	// Add User
	public void addUser(User user) {
		this.userRepository.save(user);
	}

	// Get All Users
	public List<User> getAllUser() {
		List<User> users = (List<User>) this.userRepository.findAll();
		return users;
	}

	// Get Single User
	public User getUser(int id) {
		Optional<User> optional = this.userRepository.findById(id);
		User user = optional.get();
		return user;
	}

	// Get Single User By Email
	public User getUserByEmail(String email) {
		User user = this.userRepository.findByUserEmail(email);
		return user;
	}

	// Update
	public void updateUser(User user, int id) {
		user.setUserId(id);
		this.userRepository.save(user);
	}

	// delete single User
	public void deleteUser(int id) {
		this.userRepository.deleteById(id);
	}

	//Validating user login
	public boolean validateLoginCredentials(String email, String password) {
		User user = this.userRepository.findByUserEmail(email);
		if (user != null && user.getUserPassword().equals(password) && user.getUserEmail().equals(email)) {
			return true;
		}
		return false;
	}
}
