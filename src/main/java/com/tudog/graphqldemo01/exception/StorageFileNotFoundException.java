package com.tudog.graphqldemo01.exception;

public class StorageFileNotFoundException extends StorageException{
    private static final long serialVersionUID = -5653317103633110395L;

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}