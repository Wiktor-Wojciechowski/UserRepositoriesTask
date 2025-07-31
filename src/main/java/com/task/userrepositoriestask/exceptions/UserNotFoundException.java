package com.task.userrepositoriestask.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username) {
        super("User " + username + " not found");
    }
}
