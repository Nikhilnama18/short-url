package com.nikhil.shortURL.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ShortURLResponse {
    @NonNull
    private String longURL;
    @NonNull
    private String shortURL;
    @NonNull
    private String alias;
}
