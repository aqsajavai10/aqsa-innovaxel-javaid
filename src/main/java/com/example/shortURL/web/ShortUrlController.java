package com.example.shortURL.web;


import com.example.shortURL.dao.dto.ShortUrlDto;
import com.example.shortURL.service.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shorten")
public class ShortUrlController {

    @Autowired
    private ShortUrlService service;

    @PostMapping
    public ResponseEntity<?> createShortUrl(@RequestBody Map<String, String> body) {
        if (!body.containsKey("url") || body.get("url").isEmpty()) {
            return ResponseEntity.badRequest().body("Missing URL");
        }
        ShortUrlDto dto = service.createShortUrl(body.get("url"));
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> getOriginalUrl(@PathVariable String code) {
        try {
            ShortUrlDto dto = service.getShortUrl(code);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{code}")
    public ResponseEntity<?> updateShortUrl(@PathVariable String code, @RequestBody Map<String, String> body) {
        if (!body.containsKey("url") || body.get("url").isEmpty()) {
            return ResponseEntity.badRequest().body("Missing URL");
        }
        try {
            ShortUrlDto dto = service.updateShortUrl(code, body.get("url"));
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteShortUrl(@PathVariable String code) {
        try {
            service.deleteShortUrl(code);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{code}/stats")
    public ResponseEntity<?> getStats(@PathVariable String code) {
        try {
            ShortUrlDto dto = service.getStats(code);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
