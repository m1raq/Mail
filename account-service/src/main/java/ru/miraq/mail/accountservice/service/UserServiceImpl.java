package ru.miraq.mail.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.miraq.mail.accountservice.dto.UserDto;
import ru.miraq.mail.accountservice.entity.UserEntity;
import ru.miraq.mail.accountservice.repository.UserRepository;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public void changeCharacters(UserDto userDto) {
        UserEntity userEntity = userRepository.findByUserName(userDto.getUserName());

        try {
            BeanUtils.copyProperties(userDto, userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userRepository.save(userEntity);
    }
}
