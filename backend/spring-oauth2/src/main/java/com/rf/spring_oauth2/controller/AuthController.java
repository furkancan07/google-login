package com.rf.spring_oauth2.controller;

import com.rf.spring_oauth2.dto.ApiResponse;
import com.rf.spring_oauth2.dto.AuthResponse;
import com.rf.spring_oauth2.dto.LoginRequest;
import com.rf.spring_oauth2.dto.UserDto;
import com.rf.spring_oauth2.dto.UserRegisterDto;
import com.rf.spring_oauth2.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    // register
    @PostMapping("register")
    public ApiResponse<Void> register(@Valid @RequestBody UserRegisterDto dto){
        return service.register(dto);
    }
    // login
    @PostMapping("login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request){
        int oneMonth=30*24*60*60;
        AuthResponse response=service.login(request);
        ResponseCookie cookie=ResponseCookie.from("login-token",response.getToken()).path("/").maxAge(oneMonth)
                .httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(ApiResponse.success(response,"Giriş Başarılı"));
    }
    // logout
    @DeleteMapping("logout")
    public ResponseEntity<ApiResponse<Void>> logout(@CookieValue(name = "login-token",required = false) String token){
        ResponseCookie cookie=ResponseCookie.from("login-token","").path("/").maxAge(0).httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(service.logout());

    }
}
