package ru.miraq.mail.authservice.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.miraq.mail.authservice.dto.*;
import ru.miraq.mail.authservice.entity.RefreshTokenEntity;
import ru.miraq.mail.authservice.entity.RoleType;
import ru.miraq.mail.authservice.entity.UserEntity;
import ru.miraq.mail.authservice.repository.UserRepository;
import ru.miraq.mail.authservice.security.jwt.JwtUtils;
import ru.miraq.mail.authservice.security.user.UserDetailsImpl;
import ru.miraq.mail.authservice.service.RefreshTokenService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    public AuthResponseDto authenticateUser(LoginRequestDto loginRequestDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(),
                loginRequestDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return AuthResponseDto.builder()
                .id(userDetails.getId())
                .token(jwtUtils.generateJwtToken(userDetails))
                .refreshToken(refreshToken.getToken())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();
    }

    public void register(CreateUserRequestDto createUserRequest){
        if(createUserRequest.getRoles() == null){
            ArrayList<RoleType> roles = new ArrayList<>();
            roles.add(RoleType.ROLE_USER);
            createUserRequest.setRoles(roles);
        }

        UserEntity user = new UserEntity();
        user.setUserName(createUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        user.setRoles(createUserRequest.getRoles());
        userRepository.save(user);
    }

    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request){
        String requestRefreshToken = request.getRefreshToken();

        try {
            return refreshTokenService.findByRefreshToken(requestRefreshToken)
                    .map(refreshTokenService::checkRefreshToken)
                    .map(refreshToken -> refreshToken.getUserId())
                    .map(userId -> {
                        try {
                            UserEntity tokenOwner = userRepository.findById(userId).orElseThrow(Exception::new);
                            String token = jwtUtils.generateTokenFromUsername(tokenOwner.getUserName());
                            return new RefreshTokenResponseDto(token, refreshTokenService.createRefreshToken(userId).getToken());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).orElseThrow(() -> new Exception("Refresh token not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void logout(){
        if (SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal() instanceof UserDetailsImpl userDetails){
            Long userId = userDetails.getId();

            refreshTokenService.deleteByUserId(userId);
        }
    }




}
