package com.ideas2it.logger;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * 
 * Handles custom exceptions with suitable methods that perform logging at 
 * various logging levels
 * 
 * @version 1.1 01-06-2021
 * 
 * @author Gopal G
 *
 */
public class EmployeeManagementLogger {
    private Logger logger;
   
   /**
    * Constructor with Class as argument
    * 
    * @param className Name of the class for which a logger is created
    */
    public EmployeeManagementLogger(Class<?> className) {
    	logger = LogManager.getLogger(className);
    }
    
    /**
	 * Logs debug object and message
	 * 
	 * @param message String type message
	 * @param debug Object type debug
	 */
	public void logDebug(String message, Object debug) {
		logger.debug(debug);
	}
	
    /**
	 * Logs info object and message
	 * 
	 * @param message String type message
	 * @param info Object type info
	 */
	public void logInfo(String message, Object info) {
		logger.info(info);
	}
	
	/**
	 * Logs info message
	 * @param message String type message
	 */
	public void logInfo(String message) {
		logger.info(message);
	}
	
	/**
	 * Logs warning object and message
	 * 
	 * @param message String type message
	 * @param warning Object type warning
	 */
	public void logWarn(String message, Object warning) {
		logger.warn(warning);
	}
	
	/**
	 * Logs warning message
	 * 
	 * @param message String type message
	 */
	public void logWarn(String message) {
		logger.warn(message);
	}
	
	/**
	 * Logs error object
	 * 
	 * @param error Object type error
	 */
	public void logError(Throwable throwable) {
		logger.error(throwable);
	}
	
	/**
	 * Logs fatal object
	 * 
	 * @param message String type message
	 * @param fatal Object type fatal
	 */
	public void logFatal(String message, Object fatal) {
		logger.fatal(fatal);
	}
}
