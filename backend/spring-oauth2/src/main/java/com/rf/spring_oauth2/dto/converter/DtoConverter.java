package com.rf.spring_oauth2.dto.converter;

import com.rf.spring_oauth2.dto.UserDto;
import com.rf.spring_oauth2.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {
    public UserDto convertUser(User user){
        return UserDto.builder().id(user.getId()).email(user.getEmail()).build();
    }
}
