package com.FRA.login_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FRA.login_system.entity.User;
import com.FRA.login_system.repository.UserRepository;

@Service
public class UpdateUserAccountService {

    @Autowired
    private UserRepository userRepository;

    public User updateUserAccount(String accountName, String roleType, String permissions) {
        User user = userRepository.findByUsername(accountName);

        if (user == null) {
            return null;
        }

        user.setRole(roleType);
        user.setPermissions(permissions);

        return userRepository.save(user);
    }
}