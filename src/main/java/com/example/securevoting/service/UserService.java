package com.example.securevoting.service;

import com.example.securevoting.dto.UserRegistrationRequest;
import com.example.securevoting.entity.User;

public interface UserService {

    User registerUser(UserRegistrationRequest request, boolean isAdmin);
}
