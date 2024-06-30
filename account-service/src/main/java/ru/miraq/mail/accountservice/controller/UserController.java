package ru.miraq.mail.accountservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.miraq.mail.accountservice.dto.UserDto;
import ru.miraq.mail.accountservice.service.UserServiceImpl;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class UserController {

    private final UserServiceImpl userService;

    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestBody UserDto userDto){
        userService.changeCharacters(userDto);
        return ResponseEntity.ok("User updated successfully");
    }

}
