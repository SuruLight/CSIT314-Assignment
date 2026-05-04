package com.FRA.login_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FRA.login_system.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);

    List<User> findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(
        String username,
        String fullName
    );
}
