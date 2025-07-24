package com.rf.spring_oauth2.security;

import com.rf.spring_oauth2.entity.User;
import com.rf.spring_oauth2.exception.UserNotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Configuration
public class UserIdentityManager {
    public User getUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User user){
            return user;
        }else {
            throw new UserNotFoundException();
        }
    }
}
