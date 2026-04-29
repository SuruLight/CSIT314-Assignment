package com.FRA.login_system.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.FRA.login_system.repository.UserRepository;
import com.FRA.login_system.UserAdmin;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String username, String password) {
        // Business logic: "Check credentials"
        UserAdmin userAdmin = userRepository.findByUsernameAndPassword(username, password);
        return userAdmin != null;
    }
}