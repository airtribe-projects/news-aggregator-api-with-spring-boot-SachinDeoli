package com.airtribe.NewsAggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private String title;
    private String description;
    private String url;
    private String publishedAt;
}
