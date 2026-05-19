package com.nikhil.shortURL.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class URLCacheRepository implements IURLCacheRepository {
    private static final String URL_PREFIX = "url:";
    private final StringRedisTemplate stringRedisTemplate;

    // get long url for alias
    public Optional<String> getURLByAlias(String alias){
        String key = URL_PREFIX + alias;
        String longURL = stringRedisTemplate
                .opsForValue()
                .get(key);

        return Optional.ofNullable(longURL);
    }

    // cache long url
    public void save(String longURL, String alias){
        String key = URL_PREFIX + alias;

        stringRedisTemplate.opsForValue().set(key, longURL);
    }
}
