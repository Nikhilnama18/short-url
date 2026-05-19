package com.nikhil.shortURL.service;

import com.nikhil.shortURL.dto.ShortURLResponse;
import com.nikhil.shortURL.entity.URLEntity;
import com.nikhil.shortURL.exceptions.IllegalAliasException;
import com.nikhil.shortURL.exceptions.URLNotFoundException;
import com.nikhil.shortURL.repository.URLCacheRepository;
import com.nikhil.shortURL.repository.URLRepository;
import com.nikhil.shortURL.utils.Base62Encoder;
import com.nikhil.shortURL.utils.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class URLService {

    private final URLRepository urlRepository;
    private final URLCacheRepository urlCacheRepository;
    private final SnowflakeIdGenerator snowflakeIdGenerator;
    private final Base62Encoder base62Encoder;
    @Value("${app.host-url}")
    private String hostURL;

    public ShortURLResponse createShortURL(String longURL, String alias){
            if(alias == null){
                alias = generateAlias();
            }
            try{
                URLEntity urlEntity = new URLEntity(alias, longURL);
                URLEntity savedEntity = urlRepository.save(urlEntity);

                String shortURL = generateShortURL(savedEntity.getAlias());

                return new ShortURLResponse(savedEntity.getLongURL(), savedEntity.getAlias(), shortURL);
            }catch (DataIntegrityViolationException e){
                throw new IllegalAliasException("Alias already exists");
            }
    }

    public String getLongURL(String alias){
        if (alias == null || alias.isBlank()) {
            throw new IllegalArgumentException("Alias cannot be empty");
        }

        Optional<String> cachedLongUrl = urlCacheRepository.getURLByAlias(alias);

        if(cachedLongUrl.isPresent()){
            return cachedLongUrl.get();
        }

        URLEntity urlEntity =   urlRepository.findByAlias(alias).orElseThrow(()->new URLNotFoundException(alias));

        String longURL = urlEntity.getLongURL();

        urlCacheRepository.save(longURL, alias);

        return longURL;
    }

    private String generateAlias(){
        long id = snowflakeIdGenerator.nextId();
        return base62Encoder.encode(id);
    }

    private String generateShortURL(String alias){
        return hostURL + "/" + alias;
    }
}
