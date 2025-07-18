package com.example.shortURL.dao.mapper;

import com.example.shortURL.dao.dto.ShortUrlDto;
import com.example.shortURL.dao.model.ShortUrlEntity;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlMapper {

    public ShortUrlDto toDto(ShortUrlEntity entity) {
        return new ShortUrlDto(
                entity.getId(),
                entity.getUrl(),
                entity.getShortCode(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getAccessCount()
        );
    }

    public ShortUrlEntity toEntity(ShortUrlDto dto) {
        ShortUrlEntity entity = new ShortUrlEntity();
        entity.setId(dto.getId());
        entity.setUrl(dto.getUrl());
        entity.setShortCode(dto.getShortCode());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setAccessCount(dto.getAccessCount());
        return entity;
    }
}

