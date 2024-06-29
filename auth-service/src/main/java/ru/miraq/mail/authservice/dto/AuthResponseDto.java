package ru.miraq.mail.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDto {

    private Long id;

    private String token;

    private String refreshToken;

    private String username;

    private List<String> roles;


}
