package ru.miraq.mail.authservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.miraq.mail.authservice.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select u from UserEntity u where " +
            "lower(trim(u.userName)) = lower(trim(:username)) ")
    Optional<UserEntity> findByUserName(String username);

    @Query("select u from UserEntity u "
            + "where (:userName is null OR lower(u.userName) like lower('%' || :userName || '%') ) "
            + "and (:firstName is null OR lower(u.firstName)  like  lower('%' || :firstName || '%'))")
    Page<UserEntity> advancedSearch(@Param("userName") String userName,
                                    @Param("firstName") String firstName,
                                    Pageable pageable);

    boolean existsByUserName(String userName);


}
