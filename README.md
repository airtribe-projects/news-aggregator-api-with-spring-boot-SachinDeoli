# News Aggregator API

## Overview

The **News Aggregator API** is a Spring Boot application that allows users to register, log in, and manage their news preferences. It utilizes **JWT** for secure token-based authentication and integrates with external news APIs to provide user-specific news articles.

## Features

### 1. **User**
- `POST /api/register`: Register a new user.
- `POST /api/login`: Authenticate a user and issue a JWT token.

### 2. **Verify Registration**
- `POST /api/verifyRegistration?token=<token>`: Verify a JWT token.

### 2. **News Preferences**
- `POST /api/preferences`: Create a new user's preferences.
- `GET /api/preferences`: Retrieve the logged-in user's news preferences.
- `PUT /api/preferences`: Update the logged-in user's preferences.

### 3. **News Articles**
- `GET /api/news`: Fetch personalized news articles from external APIs based on user preferences.
- `POST /api/news/{id}/read`: Mark an article as read.
- `GET /api/news/read`: Get all read articles.
- `POST /api/news/{id}/favorite`: Mark an article as favorite.
- `GET /api/news/favorite`: Get all favorite articles.

## Technologies Used

- **Spring Boot**: Core framework.
- **Spring Security**: JWT-based authentication.
- **Maven**: Build tool.
- **In-Memory Database**: Stores user data and preferences.
- **External News APIs**: Integrates with services like NewsAPI.
- **Spring WebClient**: Fetches news articles asynchronously.

## Running the Application

### Prerequisites

- **Java 21** or later
- **maven** installed

## External APIs

- **NewsAPI**: Provides articles from various sources.

> Sign up on NewsAPI platform to obtain API keys.

> Add the API key in `application.properties` file.

## Testing

Use **Postman** or **cURL** to validate API functionality.