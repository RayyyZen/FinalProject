package com.app.model;

public class Check {
    /**
     * Checks if an argument is null
     * @param obj The argument that will be checked
     * @param message The message that will be shown if the argument is null
     */
    static void checkNullArgument(Object obj, String message){
        if(message == null){
            throw new IllegalArgumentException("The null argument message is null");
        }
        if(obj == null){
            throw new IllegalArgumentException(message);
        }
    }
}
