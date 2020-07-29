package com.tudog.graphqldemo01.exception;

/**
 * 持久化异常
 */
public class PersistentException extends RuntimeException{

    private static final long serialVersionUID = -9131562360700995087L;

    public PersistentException(String message) {
        super(message);
    }

    public PersistentException(String message, Throwable cause) {
        super(message, cause);
    }
    
}