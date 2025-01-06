package com.app.auth.service;

import com.app.auth.dto.AuthenticateUserDTO;
import com.app.consent.entity.User;
import com.app.consent.exception.UserAuthorizationException;
import com.app.consent.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository        userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public User authenticate(AuthenticateUserDTO input) throws UserAuthorizationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUserName(),
                        input.getPassword()
                )
        );

        return userRepository.findByUserName(input.getUserName())
                .orElseThrow(() -> new UserAuthorizationException("User Not Found"));
    }
}
