package com.airtribe.NewsAggregator.controller;

import com.airtribe.NewsAggregator.DTOs.AuthResponse;
import com.airtribe.NewsAggregator.DTOs.UserAuthenticationResult;
import com.airtribe.NewsAggregator.DTOs.UserDTO;
import com.airtribe.NewsAggregator.entity.User;
import com.airtribe.NewsAggregator.service.UserService;
import com.airtribe.NewsAggregator.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    VerificationTokenService verificationTokenService;

    @PostMapping("/api/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userdto, HttpServletRequest request){
        User user = userService.registerUser(userdto);
        verificationTokenService.generateVerificationURL(user,getApplicationURL(request));
        return ResponseEntity.ok("User registered successfully!");
    }

    private String getApplicationURL(HttpServletRequest request){
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@Email @RequestParam("email") String email, @RequestParam("password") String password){
        UserAuthenticationResult result = userService.authenticateUser(email,password);
        if(!result.isAuthenticated()){
            if(result.getUser() == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            else if(!result.isEnabled()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User email is not verified");
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user credentials");
            }
        }
        String token = userService.generateToken(email);
        return ResponseEntity.ok(new AuthResponse(email,token));
    }

    @GetMapping("/api/hello")
    public String hello(){
        return "Hello from News Aggregator !!";
    }
}
