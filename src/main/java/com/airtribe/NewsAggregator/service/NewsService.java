package com.airtribe.NewsAggregator.service;

import com.airtribe.NewsAggregator.dto.Article;
import com.airtribe.NewsAggregator.dto.NewsResponse;
import com.airtribe.NewsAggregator.entity.NewsArticle;
import com.airtribe.NewsAggregator.entity.Preference;
import com.airtribe.NewsAggregator.entity.User;
import com.airtribe.NewsAggregator.exceptions.ResourceNotFoundException;
import com.airtribe.NewsAggregator.exceptions.PreferenceNotFoundException;
import com.airtribe.NewsAggregator.repository.NewsArticleRepository;
import com.airtribe.NewsAggregator.repository.PreferenceRepository;
import com.airtribe.NewsAggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class NewsService {

    @Autowired
    UserRepository _userRepository;

    @Autowired
    PreferenceRepository _preferenceRepository;

    @Autowired
    NewsArticleRepository _newsArticleRepository;

    @Value("${news.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "newsArticles", key = "#topic")
    private List<Article> getNewsArticleByTopic(String topic) {
        String url = "https://newsapi.org/v2/top-headlines?language=en&topic="+topic+"&apiKey="+apiKey;
        NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);
        return response != null ? response.getArticles() : Arrays.asList();
    }

    @Cacheable(value = "newsArticles", key = "#country")
    private List<Article> getNewsArticleByCountry(String country) {
        String url = "https://newsapi.org/v2/top-headlines?language=en&country="+country+"&apiKey="+apiKey;
        NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);
        return response != null ? response.getArticles() : Arrays.asList();
    }

    @Cacheable(value = "newsArticles", key = "#category")
    private List<Article> getNewsArticleByCategory(String category) {
        String url = "https://newsapi.org/v2/top-headlines?language=en&category="+category+"&apiKey="+apiKey;
        NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);
        return response != null ? response.getArticles() : Arrays.asList();
    }

    public List<Article> getNewsArticles(String username) throws PreferenceNotFoundException {
        User user = _userRepository.findByEmail(username);
        List<Article> articles = null;
        if (user == null) {
            throw new UsernameNotFoundException("User not Found");
        }
        Preference preference = _preferenceRepository.findByUser(user);
        if (preference == null) {
            throw new PreferenceNotFoundException("No Preference found for User to Update");
        }

        if (preference.getTopic() != null) {
            articles =  getNewsArticleByTopic(preference.getTopic());
        } else if (preference.getCategory() != null) {
            articles = getNewsArticleByCategory(preference.getCategory());
        }
        else{
            articles = getNewsArticleByCountry(preference.getCountry());
        }

        saveNewsArticles(articles);
        return articles;
    }

    private void saveNewsArticles(List<Article> articles){

        for(Article article : articles){
            Instant instant = Instant.parse(article.getPublishedAt());
            LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            Optional<NewsArticle> existingArticle = _newsArticleRepository.findByTitleAndUrlAndPublishedAt(article.getTitle(), article.getUrl(), localDateTime);
            if (!existingArticle.isPresent() && !article.getTitle().equals("[Removed]")) {
                NewsArticle newsArticle = new NewsArticle();
                newsArticle.setTitle(article.getTitle());
                if(article.getDescription() != null){
                    String truncatedDescription = article.getDescription().length() > 255 ? article.getDescription().substring(0, 255) : article.getDescription();
                    newsArticle.setDescription(truncatedDescription);
                }
                newsArticle.setUrl(article.getUrl());
                newsArticle.setPublishedAt(localDateTime);
                _newsArticleRepository.save(newsArticle);
            }
        }
    }

    public void markNewsAsRead(Long id) throws ResourceNotFoundException {
        NewsArticle newsArticle = _newsArticleRepository.findById(id).
                orElseThrow(() ->new ResourceNotFoundException("News Article not found with id: " +id));
        newsArticle.setRead(true);
        _newsArticleRepository.save(newsArticle);
    }

    public void markNewsAsFavorite(Long id) throws ResourceNotFoundException {
        NewsArticle newsArticle = _newsArticleRepository.findById(id).
                orElseThrow(() ->new ResourceNotFoundException("News Article not found with id: " +id));
        newsArticle.setFavourite(true);
        _newsArticleRepository.save(newsArticle);
    }

    public List<Article> getAllReadNewsArticles() {
        List<NewsArticle> newsArticles = _newsArticleRepository.findAllByIsRead(true);
        List<Article> articles = new ArrayList<>();
        for(NewsArticle newsArticle :  newsArticles){
            Article article = new Article();
            article.setTitle(newsArticle.getTitle());
            article.setDescription(newsArticle.getDescription());
            article.setUrl(newsArticle.getUrl());
            article.setPublishedAt(newsArticle.getPublishedAt().toString());
            articles.add(article);
        }
        return articles;
    }

    public List<Article> getAllFavouriteNewsArticles() {
        List<NewsArticle> newsArticles = _newsArticleRepository.findAllByIsFavourite(true);
        List<Article> articles = new ArrayList<>();
        for(NewsArticle newsArticle :  newsArticles){
            Article article = new Article();
            article.setTitle(newsArticle.getTitle());
            article.setDescription(newsArticle.getDescription());
            article.setUrl(newsArticle.getUrl());
            article.setPublishedAt(newsArticle.getPublishedAt().toString());
            articles.add(article);
        }
        return articles;
    }
}
