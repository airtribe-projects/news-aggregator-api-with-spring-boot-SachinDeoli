package com.airtribe.NewsAggregator.controller;

import com.airtribe.NewsAggregator.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenVerificationController {
    @Autowired
    VerificationTokenService verificationTokenService;

    @PostMapping("/api/verifyRegistration")
    public ResponseEntity<?> verifyRegistrationToken(@RequestParam("token") String token){
        boolean isverified = verificationTokenService.verifyTokenAndEnableUser(token);
        if(!isverified){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found / Token Expired");
        }
        return ResponseEntity.ok("Token successfully verified");
    }
}
