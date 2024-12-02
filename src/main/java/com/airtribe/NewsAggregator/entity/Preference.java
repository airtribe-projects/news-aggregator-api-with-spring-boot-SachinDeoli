package com.airtribe.NewsAggregator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long preferenceId;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @NotNull
    @Size(max = 2)
    private String country;

    private String category;

    private String topic;

    public Preference(User user, String country, String category, String topic) {
        this.user = user;
        this.country = country;
        this.category = category;
        this.topic = topic;
    }
}
