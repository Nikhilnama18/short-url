package com.nikhil.shortURL.repository;

import java.util.Optional;

public interface IURLRepository {
    public Optional<String> getURLByAlias(String alias);
    public boolean saveLongURLIfAbsent(String longURL, String alias);
}
