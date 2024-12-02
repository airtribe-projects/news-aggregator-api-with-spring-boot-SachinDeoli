package com.airtribe.NewsAggregator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long tokenId;

    private String token;

    private java.util.Date expirationTime;

    @OneToOne()
    @JoinColumn(name = "userId")
    private User user;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationTime();
    }

    public Date calculateExpirationTime() {
        long expirationTimeInMinutes = 20;
        long expirationTimeInMilliseconds = expirationTimeInMinutes * 60 * 1000;
        return new Date(System.currentTimeMillis() + expirationTimeInMilliseconds);
    }
}
