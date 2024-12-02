package com.airtribe.NewsAggregator.repository;

import com.airtribe.NewsAggregator.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    Optional<NewsArticle> findByTitleAndUrlAndPublishedAt(String title, String url, LocalDateTime publishedAt);

    List<NewsArticle> findAllByIsRead(boolean is_read);

    List<NewsArticle> findAllByIsFavourite(boolean is_favourite);
}
