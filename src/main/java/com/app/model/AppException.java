package com.app.model;

/**
 * The AppException class that inherits from the Exception class
 * @version 1.0
 * @since 1.0
 * @author Rayane
 */
public class AppException extends Exception {

    /**
     * The AppException constructor
     * @param message The message that will be displayed after throwing the exception
     */
    public AppException(String message){
        super(message);
    }
}
