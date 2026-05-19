package com.nikhil.shortURL.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
public class URLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String alias;
    @Column(nullable = false, length = 2048)
    private String longURL;
    private LocalDateTime createdAt;
}
