package com.FRA.login_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FRA.login_system.entity.User;
import com.FRA.login_system.repository.UserRepository;

@Service
public class SuspendAccountService {

    @Autowired
    private UserRepository userRepository;

    public User updateStatus(boolean suspensionState, int userID) {
        User user = userRepository.findById(userID).orElse(null);

        if (user == null) {
            return null;
        }

        user.setSuspended(suspensionState);
        return userRepository.save(user);
    }

    public User fetchUserDetails(int userID) {
        return userRepository.findById(userID).orElse(null);
    }
}