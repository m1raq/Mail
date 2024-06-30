package ru.miraq.mail.accountservice.dto;


import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.NonNull;
import ru.miraq.mail.accountservice.entity.RoleType;
import ru.miraq.mail.accountservice.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {

    @Nullable
    private Long id;

    @NonNull
    private String userName;

    @Nullable
    private String password;

    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    @Nullable
    private String middleName;

    @Nullable
    private Date birthDate;

    private boolean locked;

    @Nullable
    private List<RoleType> roles;

    @Nullable
    private UserEntity creator;

    @Nullable
    private LocalDateTime creationDate;

}
