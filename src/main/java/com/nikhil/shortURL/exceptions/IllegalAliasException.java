package com.nikhil.shortURL.exceptions;

public class IllegalAliasException extends RuntimeException{
    public IllegalAliasException(String message){
        super(message);
    }
}
