package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.configuration.UserAuthenticationProvider;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@CrossOrigin("*")
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserAuthenticationProvider userAuthenticationProvider;
	
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody User user){
		
		User user1=userService.createUser(user);
		UserDto userDto=new UserDto();
		//give him a  jwt token
		userDto.setId(user1.getId());
		userDto.setFullName(user1.getFullName());
		userDto.setEmail(user1.getEmail());
		userDto.setMobileNumber(user1.getMobileNumber());
		userDto.setPassword(user1.getPassword());
		userDto.setToken(userAuthenticationProvider.createToken(user1.getEmail()));
	
		return ResponseEntity.ok(userDto);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody User user){
		
		User user1=userService.loginUser(user);
		UserDto userDto=new UserDto();
		//give him a  jwt token
		userDto.setId(user1.getId());
		userDto.setFullName(user1.getFullName());
		userDto.setEmail(user1.getEmail());
		userDto.setMobileNumber(user1.getMobileNumber());
		userDto.setPassword(user1.getPassword());
		userDto.setToken(userAuthenticationProvider.createToken(user1.getEmail()));
		return ResponseEntity.ok(userDto);
	}
	
	@PutMapping("/updateUser")
    public ResponseEntity<User> updateUser( @RequestBody User updatedUser) {
        User updatedUserData = userService.updateUser(updatedUser);

        if (updatedUserData != null) {
            return new ResponseEntity<>(updatedUserData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        String result=userService.deleteUser(email);
        return ResponseEntity.ok(result);
    }
    
    
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
    	List<User> users=userService.getAllUser();
    	return ResponseEntity.ok(users);
    	
    }
	
}
