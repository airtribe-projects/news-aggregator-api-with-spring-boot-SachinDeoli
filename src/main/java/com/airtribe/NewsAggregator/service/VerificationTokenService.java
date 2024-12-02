package com.airtribe.NewsAggregator.service;

import com.airtribe.NewsAggregator.entity.User;
import com.airtribe.NewsAggregator.entity.VerificationToken;
import com.airtribe.NewsAggregator.repository.UserRepository;
import com.airtribe.NewsAggregator.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificationTokenService {
    @Autowired
    VerificationTokenRepository _verficationTokenRepository;

    @Autowired
    UserRepository _userRepository;

    public void generateVerificationURL(User user, String url) {
        String token = UUID.randomUUID().toString();
        String applicationUrl =  url + "/api/verifyRegistration?token=" + token;
        saveVerificationToken(user, token);
        System.out.println("Verification URL created for User " + user.getEmail() + " is " + applicationUrl);
    }

    private void saveVerificationToken(User user, String token){
        VerificationToken verificationToken = new VerificationToken(token,user);
        _verficationTokenRepository.save(verificationToken);
    }

    public boolean verifyTokenAndEnableUser(String token) {
        VerificationToken verificationToken = _verficationTokenRepository.findByToken(token);
        if(verificationToken == null || verificationToken.getExpirationTime().getTime() < System.currentTimeMillis()){
            return false;
        }
        User user = verificationToken.getUser();
        if(!user.isEnabled()){
            user.setEnabled(true);
            _userRepository.save(user);
            _verficationTokenRepository.delete(verificationToken);
        }
        else{
            _verficationTokenRepository.delete(verificationToken);
            return false;
        }
        return true;
    }
}
