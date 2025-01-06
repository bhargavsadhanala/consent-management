package com.app.auth.controller;

import com.app.auth.dto.LoginResponseDTO;
import com.app.auth.dto.AuthenticateUserDTO;
import com.app.auth.service.AuthenticationService;
import com.app.auth.service.JwtService;
import com.app.consent.entity.User;
import com.app.consent.exception.UserAuthorizationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthorizeController {

    private final JwtService            jwtService;
    private final AuthenticationService authenticationService;

    public AuthorizeController(
            JwtService jwtService,
            AuthenticationService authenticationService
    ) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<LoginResponseDTO> authenticateUser(@RequestBody AuthenticateUserDTO authenticateUserDTO)
            throws UserAuthorizationException {
        User authenticatedUser = authenticationService.authenticate(authenticateUserDTO);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponseDTO loginResponse = new LoginResponseDTO(jwtToken, jwtService.getJwtExpiration());
        return ResponseEntity.ok(loginResponse);
    }

}