package com.airtribe.NewsAggregator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotNull
    @NotBlank
    private String name;

    @Email
    private String email;

    private String password;

    private String role;

    private boolean isEnabled;

    @OneToOne(mappedBy = "user")
    private Preference preference;

    public User(Long userId, String name, String email, String password, String role, boolean isEnabled, Preference preference) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isEnabled = isEnabled;
    }
}
