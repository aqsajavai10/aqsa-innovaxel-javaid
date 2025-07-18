package com.example.shortURL.service;


import com.example.shortURL.dao.dto.ShortUrlDto;
import com.example.shortURL.dao.mapper.ShortUrlMapper;
import com.example.shortURL.dao.model.ShortUrlEntity;
import com.example.shortURL.dao.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ShortUrlService {

    @Autowired
    private ShortUrlRepository repository;

    @Autowired
    private ShortUrlMapper mapper;

    public ShortUrlDto createShortUrl(String url) {
        String shortCode = generateUniqueCode();

        ShortUrlEntity entity = new ShortUrlEntity();
        entity.setUrl(url);
        entity.setShortCode(shortCode);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setAccessCount(0);

        return mapper.toDto(repository.save(entity));
    }

    public ShortUrlDto getShortUrl(String shortCode) {
        ShortUrlEntity entity = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        entity.setAccessCount(entity.getAccessCount() + 1);
        entity.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repository.save(entity));
    }

    public ShortUrlDto updateShortUrl(String shortCode, String newUrl) {
        ShortUrlEntity entity = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        entity.setUrl(newUrl);
        entity.setUpdatedAt(LocalDateTime.now());

        return mapper.toDto(repository.save(entity));
    }

    public void deleteShortUrl(String shortCode) {
        ShortUrlEntity entity = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));
        repository.delete(entity);
    }

    public ShortUrlDto getStats(String shortCode) {
        ShortUrlEntity entity = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short URL not found"));

        return mapper.toDto(entity);
    }

    private String generateUniqueCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String code;
        do {
            code = new Random().ints(6, 0, chars.length())
                    .mapToObj(i -> String.valueOf(chars.charAt(i)))
                    .collect(Collectors.joining());
        } while (repository.findByShortCode(code).isPresent());
        return code;
    }
}

