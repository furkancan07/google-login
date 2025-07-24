package com.rf.spring_oauth2.service;

import com.rf.spring_oauth2.dto.ApiResponse;
import com.rf.spring_oauth2.dto.AuthResponse;
import com.rf.spring_oauth2.dto.LoginRequest;
import com.rf.spring_oauth2.dto.UserRegisterDto;
import com.rf.spring_oauth2.dto.converter.DtoConverter;
import com.rf.spring_oauth2.entity.User;
import com.rf.spring_oauth2.exception.LoginException;
import com.rf.spring_oauth2.security.PasswordEncoderBean;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    private final DtoConverter converter;
    public ApiResponse<Void> register(@Valid UserRegisterDto dto) {
        User user=User.builder().email(dto.getEmail()).password(encoder.encode(dto.getPassword())).build();
        userService.save(user);
        return ApiResponse.success("Kayıt Başarılı");
    }

    public AuthResponse login(@Valid LoginRequest request) {
        User user=userService.findByEmail(request.getEmail());
        if(!encoder.matches(request.getPassword(),user.getPassword())) throw new LoginException();

        String token=jwtService.createToken(request.getEmail());
        AuthResponse dto=AuthResponse.builder().user(converter.convertUser(user)).token(token).build();
        return dto;
    }
    public ApiResponse<Void> logout(){
        return ApiResponse.success("Çıkış Yapıldı");
    }
}
