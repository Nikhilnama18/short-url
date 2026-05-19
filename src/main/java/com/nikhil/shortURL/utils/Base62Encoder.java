package com.nikhil.shortURL.utils;


import org.springframework.context.annotation.Configuration;

@Configuration
public class Base62Encoder {

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String encode(long value) {
        if (value == 0) {
            return "0";
        }

        StringBuilder result = new StringBuilder();

        while (value > 0) {
            int remainder = (int) (value % 62);
            result.append(BASE62.charAt(remainder));
            value = value / 62;
        }

        return result.reverse().toString();
    }
}
