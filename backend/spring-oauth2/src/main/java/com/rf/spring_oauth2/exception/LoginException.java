package com.rf.spring_oauth2.exception;

public class LoginException extends RuntimeException{
    public LoginException() {
        super("Kullanici adi veya şifre hatalı");
    }
}
