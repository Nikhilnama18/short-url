package com.nikhil.shortURL.repository;

import com.nikhil.shortURL.entity.URLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLRepository extends JpaRepository<URLEntity, Long>{
    Optional<URLEntity> findByAlias(String alias);
    boolean existsByAlias(String alias);
}
