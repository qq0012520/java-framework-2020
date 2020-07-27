package com.tudog.graphqldemo01.exception;

public class StorageException extends RuntimeException{
    
    private static final long serialVersionUID = 5880823966005606384L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
    
}