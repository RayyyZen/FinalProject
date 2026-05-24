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

    /**
     * Checks if an argument of any method of a class is null
     * @param obj The argument that will be checked
     * @param className The class name
     * @param attribute The attribute of the class that is null
     */
    static void checkClassNullArgument(Object obj, String className, String attribute){
        checkNullArgument(obj, "The " + attribute + " of the " + className + " is null !");
    }
}