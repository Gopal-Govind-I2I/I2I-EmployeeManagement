package com.ideas2it.employeemanagement.service;

import java.util.List;
import java.util.Set;
import java.sql.Date;

import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.exception.EmployeeManagementException;

/**
 * EmployeeService is an interface for employee management operations at service layer.
 *
 * @version 1.0   29-04-2021
 *
 * @author Gopal G
 */
public interface EmployeeService {
   /**
    * Validates employee ID by verifying whether the same ID already exists or not.
    *
    * @param id ID value which is to be validated
    * @param mode Mode for check
    *
    * @return a boolean to denote successful/unsuccessful validation of new employee ID
    */
    public boolean validateEmployeeID(String employeeID, int mode) throws EmployeeManagementException;
	
   /**
    * ID validation check before insertion
    *
    * @param id ID value which is to be checked
    *
    * @return a boolean to denote the existence status of the given employee ID
    */
    public boolean checkIfIdExists(String id) throws EmployeeManagementException;
	
   /**
    * Employee object exposure for authorized purposes at Project Service layer
    *
    * @param id ID of the employee whose instance is to be fetched
    *
    * @return employee Instance of the given employee
    */
    public Employee exposeEmployee(String id) throws EmployeeManagementException;

   /**
    * Returns date of birth of Date type by validating the string value of DOB
    *
    * @param birthDate String value of DOB which is to be validated
    *
    * @return dateOfBirth Date type value of DOB on successful validation
    */
    public Date getBirthDate(String birthDate);
	
   /**
    * Validates email ID using email regex pattern
    *
    * @param email String value of email ID which is to be validated
    *
    * @return a boolean to denote successful/unsuccessful validation of given email ID
    */
    public boolean validateEmail(String email);

   /**
    * Searches for an employee using employee ID
    *
    * @param employeeID ID value of the employee which is the search key
    *
    * @return Details of the searched employee concatenated together in
    * string format, on successful finding of the employee.
    */
    public String searchEmployeeID(String employeeID) throws EmployeeManagementException;
	
   /**
    * Searches for an employee using employee ID
    *
    * @param employeeID ID value of the employee which is the search key
    *
    * @return Details of the searched employee in a list of
    *         string arrays format, on successful finding of the employee.
    */
    public List<String[]> searchIndividualEmployee(String employeeID) throws EmployeeManagementException;
	
   /**
    * Deletes the employee with given ID, if the ID exists
    *
    * @param employeeID id of the employee who is to be deleted
    *
    * @return a boolean to denote successful/unsuccessful deletion of employee
    */
    public boolean deleteEmployee(String employeeID) throws EmployeeManagementException;
	
   /**
    * Restores the employee with given ID, if the ID exists and has been inactive
    *
    * @param employeeID id of the employee who is to be restored
    *
    * @return a boolean to denote successful/unsuccessful restoration of employee
    */
    public boolean restoreEmployee(String employeeID) throws EmployeeManagementException;
	
   /**
    * Prepares a list of all employees on request
    *
    * @return a list of all employees with their details concatenated
    * as individual strings
    */
    public List<String> getAllEmployees() throws EmployeeManagementException;
	
   /**
    * Prepares a list of all deleted employees on request
    *
    * @return a list of all deleted employees with their details in a list of
    *         list of string arrays
    */
    public List<List<String[]>> getDeletedEmployees() throws EmployeeManagementException;
	
	
   /**
    * Prepares a list of all deleted employees on request
    *
    * @return a list of all deleted employees with their details in a list of
    *         list of string arrays
    */
    public List<List<String[]>> fetchAllEmployees() throws EmployeeManagementException;
	
   /**
    * Prepares a list of all employees on request
    *
    * @return a list of all employees in model form
    * 
    */
    public List<Employee> getEmployeeModelList() throws EmployeeManagementException;
	
   /**
    * Constructs the details of employees in a list format with necessary metadata information
	* with string arrays
	* 
	* @param employee Instance of the given employee
	*
	* @return employeeDetails List of staing arrays with employee data and associated metadata information
    */
    public List<String[]> constructEmployeeDetails(Employee employee);
	
   /**
    * Updates all details of the employee with the given ID
    *
    * @param employeeID ID value of the employee whose details are updated
    * @param name New update value for name
    * @param id New update value for id
    * @param salary New update value for salary
    * @param email New update value for email id
    * @param dateOfBirth New update value for date of birth
    *
    * @return a boolean to specify successful/unsuccessful update of employee details
    */
    public boolean updateEmployeeDetails(String employeeID, String employeeName, 
                                        Date employeeDateOfBirth, double employeeSalary, 
                                        String employeeEmailID) throws EmployeeManagementException;
    
    /**
     * {@inheritdoc}
     */
     public boolean updateExistingAddress(int addressID, String doorNumber, String street,
                                          String locality, String pincode, String district,
                                          String state, String employeeID) throws EmployeeManagementException;
	
   /**
    * Passes on the address list request to DAO layer and returns the resource to controller
    *
    * @param employeeID id of the employee whose address list is requested
    *
    * @return a list of String arrays carrying address details of the employee
    */
    public List<String[]> getEmployeeAddressList(String employeeID) throws EmployeeManagementException;

   /**
    * Receives the add new address request from controller and passes it on to DAO layer
    *
    * @param employeeID id of the employee whose new address is added
    * @param addressDetails A String array with details of the new address
    *
    * @return a boolean to denote successful/unsuccessful addition of new address
    */
    public boolean addNewAddress(String employeeID, String[] addressDetails) throws EmployeeManagementException;
    
   /**
    * {@inheritdoc}
    */
    public boolean deleteAddress(int addressID, String employeeID) throws EmployeeManagementException;
     
   /**
    * {@inheritdoc}
    */
    public boolean updatePermanentAddress(int oldPermanentAddress, int newPermanentAddress, String employeeID) throws EmployeeManagementException;
      
   /**
    * {@inheritdoc}
    */
    public boolean unassignProject(String employeeID, int projectID) throws EmployeeManagementException;
       
   /**
    * {@inheritdoc}
    */
    public List<String[]> getAssignableProjects(String employeeID) throws EmployeeManagementException;
        
   /**
    * {@inheritdoc}
    */
    public List<Integer> assignProject(String employeeID, Set<Integer> projectIdSet) throws EmployeeManagementException;
		
   /**
    * Creates a new employee with given information and adds the employee to roster
    *
    * @param name Name of the employee
    * @param employeeID ID of the employee
    * @param salary Salary of the employee
    * @param dateOfBirth Date of birth of the employee
    * @param email Email ID of the employee
    * @param addresses List of String arrays carrying address details
    *
    * @return a boolean to specify successful/unsuccessful addition of an employee
    */		
    public boolean addEmployee(String name, String employeeID, double salary,
                              Date dateOfBirth, String email, List<String[]> addresses) throws EmployeeManagementException;
}