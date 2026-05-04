package com.FRA.login_system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FRA.login_system.entity.User;
import com.FRA.login_system.service.UpdateUserAccountService;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "*")
public class UpdateUserAccountController {

    @Autowired
    private UpdateUserAccountService updateUserAccountService;

    @PutMapping("/update")
    public User updateUserAccount(@RequestBody Map<String, String> request) {
        String accountName = request.get("accountName");
        String roleType = request.get("roleType");
        String permissions = request.get("permissions");

        return updateUserAccountService.updateUserAccount(accountName, roleType, permissions);
    }
}