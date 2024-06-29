package ru.miraq.mail.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginRequestDto  implements Serializable {

    private String username;

    private String password;
}
