package com.rf.spring_oauth2.service;

import com.rf.spring_oauth2.entity.User;
import com.rf.spring_oauth2.exception.UserNotFoundException;
import com.rf.spring_oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }

    private final UserRepository repository;

    public boolean existByEmail(String email) {
        return repository.existsByEmail(email);
    }

    protected void save(User user) {
        repository.save(user);
    }

    protected User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(()->new UserNotFoundException());
    }
}
