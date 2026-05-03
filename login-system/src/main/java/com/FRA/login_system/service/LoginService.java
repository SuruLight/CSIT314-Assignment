package com.FRA.login_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FRA.login_system.entity.User;
import com.FRA.login_system.repository.UserRepository;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String username, String password) {
        // Business logic: "Check credentials"
        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user == null) {
            return false;
        }

        if (user.isSuspended()) {
            return false;
        }

        return true;
    }
}