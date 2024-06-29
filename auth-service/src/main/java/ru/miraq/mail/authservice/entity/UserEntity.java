package ru.miraq.mail.authservice.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", unique = true, length = 50)
    private String userName;

    @Column(name = "password", length = 150)
    private String password;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "is_locked", nullable = false)
    private boolean locked;

    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<RoleType> roles;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="creator")
    private UserEntity creator;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;


}
