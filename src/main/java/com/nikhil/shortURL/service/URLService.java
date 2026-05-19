package com.nikhil.shortURL.service;

import com.nikhil.shortURL.dto.ShortURLResponse;
import com.nikhil.shortURL.exceptions.IllegalAliasException;
import com.nikhil.shortURL.exceptions.URLNotFoundException;
import com.nikhil.shortURL.repository.URLRepository;
import com.nikhil.shortURL.utils.Base62Encoder;
import com.nikhil.shortURL.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class URLService {

    private final URLRepository urlRepository;
    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final Base62Encoder base62Encoder;
    @Value("${app.host-url}")
    private String hostURL;

    public ShortURLResponse createShortURL(String longURL, String alias){
            if(alias == null){
                alias = generateAlias();
            }

            boolean saved = urlRepository.saveLongURLIfAbsent(longURL, alias);
            if(!saved){
                throw new IllegalAliasException("Alias already exists");
            }

            String shortURL = generateShortURL(alias);

            return new ShortURLResponse(longURL, alias, shortURL);
    }

    public String getLongURL(String alias){
        if (alias == null || alias.isBlank()) {
            throw new IllegalArgumentException("Alias cannot be empty");
        }
        return urlRepository.getURLByAlias(alias).orElseThrow(()->new URLNotFoundException(alias));
    }



    private String generateAlias(){
        long id = snowflakeIdGenerator.nextId();
        return base62Encoder.encode(id);
    }

    private String generateShortURL(String alias){
        return hostURL + "/" + alias;
    }
}
