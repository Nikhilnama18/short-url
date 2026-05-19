package com.nikhil.shortURL.controller;

import com.nikhil.shortURL.dto.ShortURLRequest;
import com.nikhil.shortURL.dto.ShortURLResponse;
import com.nikhil.shortURL.service.URLService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class URLController {

    private final URLService urlService;

    @PostMapping("/api/url")
    public ResponseEntity<ShortURLResponse> createShortURL(@Valid  @RequestBody ShortURLRequest request){
        ShortURLResponse response = urlService.createShortURL(request.getLongURL(), request.getAlias());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{alias}")
    public ResponseEntity<Void> redirectToLongURL(@PathVariable String alias){
        String longURL= urlService.getLongURL(alias);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(longURL))
                .build();
    }
}
