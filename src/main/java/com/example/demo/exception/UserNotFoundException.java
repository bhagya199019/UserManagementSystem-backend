package com.example.demo.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String email) {
        super("User with email " + email + " not found");
    }
}
