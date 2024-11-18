package com.airtribe.NewsAggregator.repository;

import com.airtribe.NewsAggregator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegisteration extends JpaRepository<User, Long> {

}
