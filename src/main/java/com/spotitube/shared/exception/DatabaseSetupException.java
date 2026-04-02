package com.spotitube.shared.exception;

public class DatabaseSetupException extends RuntimeException{
    public DatabaseSetupException(String message){
        super(message);
    }
}
