package com.example.emailsApp.services;

import java.util.List;

import com.example.emailsApp.dto.UserDto;
import com.example.emailsApp.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
