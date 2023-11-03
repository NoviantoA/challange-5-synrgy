package com.novianto.challange5.service.impl;

import com.novianto.challange5.dto.UserDto;
import com.novianto.challange5.entity.User;
import com.novianto.challange5.repository.UserRepository;
import com.novianto.challange5.service.UserService;
import com.novianto.challange5.util.ConfigValidation;
import com.novianto.challange5.util.PasswordEncoder;
import com.novianto.challange5.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Response response;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Map<String, Object> saveUser(UserDto userDto) {
        Map<String, Object> responseMap = new HashMap<>();

        if (userDto == null) {
            return response.errorResponse(ConfigValidation.USER_DATA_INVALID, ConfigValidation.STATUS_CODE_BAD_REQUEST);
        } else {
            if (!response.isValidName(userDto.getUsername())) {
                return response.errorResponse(ConfigValidation.USERNAME_NOT_VALID, ConfigValidation.STATUS_CODE_BAD_REQUEST);
            } else if (!response.isValidEmail(userDto.getEmailAddress())) {
                return response.errorResponse(ConfigValidation.EMAIL_NOT_VALID, ConfigValidation.STATUS_CODE_BAD_REQUEST);
            }
        }

        try {
            User user = new User();
            user.setId(UUID.randomUUID());
            user.setUsername(userDto.getUsername());
            user.setEmailAddress(userDto.getEmailAddress());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));

            Optional<User> optionalUser = Optional.of(userRepository.save(user));

            responseMap = response.successResponse(optionalUser.get());
        } catch (DataAccessException e) {
            responseMap = response.errorResponse(e.getMessage(), ConfigValidation.STATUS_CODE_INTERNAL_SERVER_ERROR);
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> updateUser(UUID idUser, UserDto userDto) {
        Map<String, Object> responseMap = new HashMap<>();

        try {
            Optional<User> existingUser = userRepository.findById(idUser);

            if (existingUser.isPresent()) {
                User updatedUser = existingUser.get();

                if (userDto.getUsername() != null) {
                    updatedUser.setUsername(userDto.getUsername());
                }
                if (userDto.getEmailAddress() != null) {
                    updatedUser.setEmailAddress(userDto.getEmailAddress());
                }
                if (userDto.getPassword() != null) {
                    updatedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
                }
                User savedUser = userRepository.save(updatedUser);
                responseMap = response.successResponse(savedUser);
            } else {
                responseMap = response.errorResponse(ConfigValidation.ID_USER_NOT_FOUND, ConfigValidation.STATUS_CODE_NOT_FOUND);
            }
        } catch (Exception e) {
            responseMap = response.errorResponse(e.getMessage(), ConfigValidation.STATUS_CODE_INTERNAL_SERVER_ERROR);
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> deleteUser(UUID idUser) {
        Map<String, Object> responseMap = new HashMap<>();

        try {
            Optional<User> userToDelete = Optional.ofNullable(userRepository.getByIdUser(idUser));

            if (userToDelete.isPresent()) {
                User user = userToDelete.get();
                user.setDeleted_date(new Date());
                userRepository.save(user);

                responseMap = response.successResponse(ConfigValidation.SUCCESS);
            } else {
                responseMap = response.errorResponse(ConfigValidation.ID_USER_NOT_FOUND, ConfigValidation.STATUS_CODE_NOT_FOUND);
            }
        } catch (Exception e) {
            responseMap = response.errorResponse(e.getMessage(), ConfigValidation.STATUS_CODE_INTERNAL_SERVER_ERROR);
        }
        return responseMap;
    }

    @Override
    public Map<String, Object> getUserById(UUID idUser) {
        Map<String, Object> responseMap = new HashMap<>();

        Optional<User> userOptional = Optional.ofNullable(userRepository.getByIdUser(idUser));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            responseMap = response.successResponse(user);
        } else {
            responseMap = response.errorResponse(ConfigValidation.ID_USER_NOT_FOUND, ConfigValidation.STATUS_CODE_NOT_FOUND);
        }
        return responseMap;
    }
}
