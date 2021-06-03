package com.ideas2it.exception;

/**
 * 
 * Handles custom exceptions by extending Exception
 * 
 * @version 1.1 01-06-2021
 * 
 * @author Gopal G
 *
 */
public class EmployeeManagementException extends Exception {

   /**
    * Constructor with message string as argument
    * 
    * @param message String type message value
    */
    public EmployeeManagementException(String message) {
        super(message);
    }
	
   /**
    * Constructor with throwable argument
    * 
    * @param throwable Instance of type throwable
    */
    public EmployeeManagementException(Throwable throwable) {
        super(throwable);
    }
	
   /**
    * Constructor with message string and throwable instance as arguments
    * 
    * @param message String type message value
    * @param throwable Instance of type throwable
    */
    public EmployeeManagementException(String message, Throwable throwable) {
        super(message, throwable);
    }
}