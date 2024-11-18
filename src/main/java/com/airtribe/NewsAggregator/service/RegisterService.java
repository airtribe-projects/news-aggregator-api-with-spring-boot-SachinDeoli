package com.airtribe.NewsAggregator.service;

import com.airtribe.NewsAggregator.entity.User;
import com.airtribe.NewsAggregator.repository.IRegisteration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private IRegisteration registeration;

    public String registerUser(User user) {
        registeration.save(user);
        return "User registered successfully";
    }
}
