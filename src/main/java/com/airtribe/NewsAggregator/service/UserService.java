package com.airtribe.NewsAggregator.service;

import com.airtribe.NewsAggregator.DTOs.UserAuthenticationResult;
import com.airtribe.NewsAggregator.DTOs.UserDTO;
import com.airtribe.NewsAggregator.JwtUtil.JwtUtil;
import com.airtribe.NewsAggregator.entity.User;
import com.airtribe.NewsAggregator.repository.UserRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository _userRepository;

    @Autowired
    PasswordEncoder _passEncoder;

    public User registerUser(UserDTO userdto) {
        User user = new User();
        user.setName(userdto.getName());
        user.setEmail(userdto.getEmail());
        user.setPassword(_passEncoder.encode(userdto.getPassword()));
        user.setRole("User");
        user.setEnabled(false);
        return _userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = _userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }

    public UserAuthenticationResult authenticateUser(@Email String email, String password) {
        UserAuthenticationResult result = new UserAuthenticationResult();

        User user = _userRepository.findByEmail(email);
        result.setUser(user);
        if(user == null || !user.isEnabled()){
            return result;
        }

        result.setEnabled(user.isEnabled());
        if(!_passEncoder.matches(password,user.getPassword())){
            return result;
        }

        result.setAuthenticated(true);
        UserDetails userDetails = loadUserByUsername(email);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return result;
    }

    public String generateToken(@Email String email) {
        return JwtUtil.generateToken(email);
    }
}
