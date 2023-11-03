package com.novianto.challange5.service;

import com.novianto.challange5.dto.UserDto;
import com.novianto.challange5.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.UUID;

public interface UserService {

    Page<User> getAllUser(Pageable pageable);

    Map<String, Object> saveUser(UserDto userDto);

    Map<String, Object> updateUser(UUID idUser, UserDto userDto);

    Map<String, Object> deleteUser(UUID idUser);

    Map<String, Object> getUserById(UUID idUser);
}
