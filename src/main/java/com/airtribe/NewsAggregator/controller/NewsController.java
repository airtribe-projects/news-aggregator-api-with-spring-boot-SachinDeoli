package com.airtribe.NewsAggregator.controller;

import com.airtribe.NewsAggregator.DTOs.Article;
import com.airtribe.NewsAggregator.exceptions.NewsNotFoundException;
import com.airtribe.NewsAggregator.exceptions.UserPreferenceNotFoundException;
import com.airtribe.NewsAggregator.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NewsController {
    @Autowired
    NewsService newsService;

    @GetMapping("/api/news")
    public ResponseEntity<?> getNews(Authentication authentication) throws UserPreferenceNotFoundException {
        String username = authentication.getName();
        List<Article> articles = newsService.getNewsArticles(username);
        if(articles.size() == 0){
            return ResponseEntity.ok("No articles for the mentioned preference");
        }
        return ResponseEntity.ok(articles);
    }

    @PostMapping("/api/news/{id}/read")
    public ResponseEntity<?> markNewsAsRead(Authentication authentication, @PathVariable Long id) throws NewsNotFoundException {
        newsService.markNewsAsRead(id);
        return ResponseEntity.ok("News Marked as Read");
    }

    @PostMapping("/api/news/{id}/favorite")
    public ResponseEntity<?> markNewsAsFavorite(Authentication authentication, @PathVariable Long id) throws NewsNotFoundException {
        newsService.markNewsAsFavorite(id);
        return ResponseEntity.ok("News Marked as Favourite");
    }

    @GetMapping("/api/news/read")
    public ResponseEntity<?> getAllReadNews(Authentication authentication) {
        List<Article> articles = newsService.getAllReadNewsArticles();
        if(articles.size() == 0){
            return ResponseEntity.ok("No read news articles");
        }
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/api/news/favorites")
    public ResponseEntity<?> getAllFavouriteNews(Authentication authentication) {
        List<Article> articles = newsService.getAllFavouriteNewsArticles();
        if(articles.size() == 0){
            return ResponseEntity.ok("No favourite news articles");
        }
        return ResponseEntity.ok(articles);
    }
}
