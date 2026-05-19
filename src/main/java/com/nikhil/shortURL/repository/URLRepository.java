package com.nikhil.shortURL.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class URLRepository implements IURLRepository {
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

    // store long url if alias didn't exist
    public boolean saveLongURLIfAbsent(String longURL, String alias){
        String key = URL_PREFIX + alias;

        Boolean saved = stringRedisTemplate
                .opsForValue()
                .setIfAbsent(key, longURL);

        return Boolean.TRUE.equals(saved);
    }
}
