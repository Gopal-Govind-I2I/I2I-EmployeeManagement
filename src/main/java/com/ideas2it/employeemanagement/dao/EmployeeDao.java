package com.ideas2it.employeemanagement.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.exception.EmployeeManagementException;

/**
 * EmployeeDao interface provides the method signatures for performing Employee CRUD operations At DB
 *
 * @version 1.1   30-04-2021
 *
 * @author Gopal G
 */
public interface EmployeeDao {
	
   /**
    * Adds an employee record to the database with given employee data and makes call to subsequent
    * address addition method
    *
    * @param employee an Employee object with details to be added to the database
    *
    * @return a boolean to denote successful/unsuccessful addition of employee record to database
    */
    public boolean addEmployee(Employee employee) throws EmployeeManagementException;
    
    
    /**
     * Fetches an individual employee's information with employee ID as key
     * 
     * @param employeeID id of the employee who is searched
     * 
     * @return employee Instance of the employee which is the search result
     */
     public Employee fetchIndividualEmployee(String employeeID) throws EmployeeManagementException;
	
   /**
    * Fetches the details of all employees and returns the collected data as a list of individual
    * employee objects
    *
    * @return employeeList A list of individual Employee objects laden with corresponding details
    *         fetched from the database
    */
    public List<Employee> getAllEmployees() throws EmployeeManagementException;
	
   /**
    * Updates an employee record in the database
    *
    * @param employeeID id of the employee whose record in the database is to be updated
    *
    * @return a boolean that denotes successful/unsuccessful update of employee record in the database
    */
    public boolean updateEmployeeDetails(Employee employee) throws EmployeeManagementException;
    
    /**
     * Fetches all deleted employees
     * 
     * @return deletedEmployees a list of all deleted employees
     */
     public List<Employee> getDeletedEmployees() throws EmployeeManagementException;
     
    /**
     * Closes a session object
     */
     public void closeSession(Session session);
}