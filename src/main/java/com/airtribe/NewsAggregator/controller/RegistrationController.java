package com.airtribe.NewsAggregator.controller;

import com.airtribe.NewsAggregator.entity.User;
import com.airtribe.NewsAggregator.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private RegisterService register;

    @PostMapping("/api/register")
    public String registerUser(@RequestBody User user) {
        return register.registerUser(user);
    }
}
