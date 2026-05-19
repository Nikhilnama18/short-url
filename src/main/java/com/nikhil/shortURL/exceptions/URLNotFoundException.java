package com.nikhil.shortURL.exceptions;

public class URLNotFoundException extends RuntimeException{
    public URLNotFoundException(String alias){
        super("No URL for alias " +alias);
    }
}
