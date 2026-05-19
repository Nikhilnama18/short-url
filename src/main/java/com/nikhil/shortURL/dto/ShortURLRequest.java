package com.nikhil.shortURL.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortURLRequest {
    private String longURL;
    private String alias;
}
