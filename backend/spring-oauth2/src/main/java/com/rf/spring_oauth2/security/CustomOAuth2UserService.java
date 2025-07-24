package com.rf.spring_oauth2.security;

import com.rf.spring_oauth2.entity.User;
import com.rf.spring_oauth2.model.ROLE;
import com.rf.spring_oauth2.repository.UserRepository;
import com.rf.spring_oauth2.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOAuth2UserService extends OidcUserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public CustomOAuth2UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Google OAuth2 ile kullanıcı yükleniyor...");
        OidcUser oidcUser = super.loadUser(userRequest);

        String email = oidcUser.getAttribute("email");
        log.debug("Kullanıcıdan alınan e-posta: {}", email);

        if (email == null) {
            log.error("Email bulunamadı, authentication başarısız!");
            throw new OAuth2AuthenticationException("Email bulunamadı!");
        }

        User user = userRepository.findByEmail(email).orElseGet(() -> {
            log.info("Kullanıcı sistemde bulunamadı, yeni kullanıcı oluşturuluyor: {}", email);
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setRole(ROLE.ROLE_USER);
            return userRepository.save(newUser);
        });

        log.info("Kullanıcı başarıyla yüklendi: {}", user.getEmail());

        String token = jwtService.createToken(email);
        log.debug("JWT Token oluşturuldu: {}", token);

        return new CustomOAuth2User(oidcUser, token, user);
    }
}
