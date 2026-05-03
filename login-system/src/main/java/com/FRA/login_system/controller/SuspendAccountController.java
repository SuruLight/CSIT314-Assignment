package com.FRA.login_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FRA.login_system.entity.User;
import com.FRA.login_system.service.SuspendAccountService;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "*")
public class SuspendAccountController {

    @Autowired
    private SuspendAccountService suspendAccountService;

    @PutMapping("/{userID}/suspend")
    public User updateStatus(@PathVariable int userID) {
        return suspendAccountService.updateStatus(true, userID);
    }

    @PutMapping("/{userID}/unsuspend")
    public User unsuspend(@PathVariable int userID) {
        return suspendAccountService.updateStatus(false, userID);
    }

    @GetMapping("/{userID}")
    public User fetchUserDetails(@PathVariable int userID) {
        return suspendAccountService.fetchUserDetails(userID);
    }
}