package ru.miraq.mail.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.miraq.mail.authservice.dto.*;
import ru.miraq.mail.authservice.security.service.SecurityService;


@RestController
public class AuthController {

    private final SecurityService securityService;

    @Autowired
    public AuthController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(securityService.authenticateUser(loginRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> register(@RequestBody CreateUserRequestDto createUserRequestDto){
        securityService.register(createUserRequestDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RefreshTokenResponseDto> updateRefreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        return ResponseEntity.ok(securityService.refreshToken(refreshTokenRequestDto));
    }

}
