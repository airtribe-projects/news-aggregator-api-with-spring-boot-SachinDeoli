package com.airtribe.NewsAggregator.dto;

import com.airtribe.NewsAggregator.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationResult {
    private boolean isAuthenticated;
    private boolean isEnabled;
    private User user;
}
