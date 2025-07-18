package com.example.shortURL.dao.repository;


import com.example.shortURL.dao.model.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {
    Optional<ShortUrlEntity> findByShortCode(String shortCode);
    void deleteByShortCode(String shortCode);
}

