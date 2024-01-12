package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	

public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User user1=userRepository.save(user);
		if(user1 !=null) {
			return user1;
		}
		
		return null;
	}
	

public User loginUser(User user) {
	User user1 =userRepository.findByEmail(user.getEmail());
    
	if(passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
	
		return user1;
	}
		return null;
	}


	
 public User findByEmail(String email) {
	   return userRepository.findByEmail(email);
   }

 
  public User updateUser(User updatedUser) {
	    try {
	        User existingUser = userRepository.findByEmail(updatedUser.getEmail());
	        if (existingUser != null) {
	            // Update user fields as needed
	        	existingUser.setFullName(updatedUser.getFullName());            
	        	existingUser.setEmail(updatedUser.getEmail());
	            existingUser.setMobileNumber(updatedUser.getMobileNumber());

	            // Check if the password is not empty and encode it
	            String newPassword = updatedUser.getPassword();
	            if (newPassword != null && !newPassword.isEmpty()) {
	                String encodedPassword = passwordEncoder.encode(newPassword);
	                existingUser.setPassword(encodedPassword);
	            }

	         // Save the updated user
	            return userRepository.save(existingUser);
	        } else {
	        	throw new UserNotFoundException("User with email " + updatedUser.getEmail() + " not found");
	            // Handle the case where existingUser is null (e.g., throw an exception or return a default value)
	        }
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	    }

	    return null; 
	}

	

public String deleteUser(String email) {
		User user1=userRepository.findByEmail(email);
		userRepository.delete(user1);
		return "User Deleted Successfully";
	}
	

public List<User> getAllUser() {
		List<User> users=userRepository.findAll();
		return users;
		
	}
	
	
}
