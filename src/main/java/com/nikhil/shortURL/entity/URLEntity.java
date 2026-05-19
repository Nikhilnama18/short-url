package com.nikhil.shortURL.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
@Getter
@NoArgsConstructor
public class URLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String alias;
    @Column(nullable = false, length = 2048)
    private String longURL;
    private LocalDateTime createdAt;

    public URLEntity(String alias, String longURL){
        this.alias = alias;
        this.longURL = longURL;
        this.createdAt = LocalDateTime.now();
    }
}
