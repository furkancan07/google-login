package com.rf.spring_oauth2.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Kullanıcı Bulunamadi");
    }
}
