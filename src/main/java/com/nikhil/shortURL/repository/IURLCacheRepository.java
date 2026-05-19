package com.nikhil.shortURL.repository;

import java.util.Optional;

public interface IURLCacheRepository {
    public Optional<String> getURLByAlias(String alias);
    public void save(String longURL, String alias);
}
