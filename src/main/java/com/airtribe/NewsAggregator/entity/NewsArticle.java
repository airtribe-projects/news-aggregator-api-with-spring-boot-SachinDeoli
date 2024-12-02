package com.airtribe.NewsAggregator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long newsArticleId;

    @NotNull
    private String title;

    @NotNull
    private String url;

    private String description;

    @NotNull
    private LocalDateTime publishedAt;

    private boolean isRead;

    private boolean isFavourite;

    public NewsArticle(String title, String url, String description, LocalDateTime publishedDate) {
        this.title = title;
        this.url = url;
        this.description = description;
        this.publishedAt = publishedDate;
        this.isRead = false;
        this.isFavourite = false;
    }
}
