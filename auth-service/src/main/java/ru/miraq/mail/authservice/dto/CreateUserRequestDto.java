package ru.miraq.mail.authservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.miraq.mail.authservice.entity.RoleType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequestDto {

    private String username;

    private List<RoleType> roles;

    private String password;


}
