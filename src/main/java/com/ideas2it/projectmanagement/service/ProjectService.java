package com.ideas2it.projectmanagement.service;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.ideas2it.exception.EmployeeManagementException;
import com.ideas2it.projectmanagement.model.Project;

/**
 * ProjectService interface has method signatures for project CRUD service layer operations
 *
 * @version 1.0   01-05-2021
 *
 * @author Gopal G
 */

public interface ProjectService {
   /**
    * Returns deadline of Date type by validating the string value of Deadline date
    *
    * @param deadlineDate String value of deadline which is to be validated
    *
    * @return deadline Date type value of deadline on successful validation
    */
    public Date getDeadline(String deadlineDate);
	
   /**
    * Project object exposure for authorized purposes at Employee Service layer
    *
    * @param id ID of the project whose instance is to be fetched
    *
    * @return project Instance of the given project
    */
    public Project exposeProject(int id) throws EmployeeManagementException;
	
   /**
    * Creates a new project with given information
    *
    * @param name Name of the project
    * @param manager Manager of the project
    * @param client Client of the project
    * @param deadline Deadline of the project
    *
    * @return a boolean to specify successful/unsuccessful addition of project
    */
    public boolean addProject(String name, String manager, String client, Date deadline) throws EmployeeManagementException;
	
   /**
    * Validates project ID by verifying whether the same ID already exists or not.
    *
    * @param projectID ID value which is to be validated
    * @param mode Mode for check
    *
    * @return a boolean to denote successful/unsuccessful validation of project ID
    */
    public boolean checkProjectID(int projectID, int mode) throws EmployeeManagementException;
	
   /**
    * Restores the project with given ID, if the ID has been rendered inactive
    *
    * @param projectID ID value of the project which is to be restored
    *
    * @return success a boolean to denote successful/unsuccessful restoration of project
    */
    public boolean restoreProject(int projectID) throws EmployeeManagementException;
    
   /**
    * {@inheritdoc}
    */
    public List<List<String[]>> fetchAllProjects() throws EmployeeManagementException;
     
   /**
    * {@inheritdoc}
    */
    public List<String[]> searchIndividualProject(int projectID) throws EmployeeManagementException;

   /**
    * {@inheritdoc}
    */
    public List<List<String[]>> getDeletedProjects() throws EmployeeManagementException;
	
   /**
    * Deletes the project with given ID, if the ID exists
    *
    * @param projectID ID value of the project which is to be deleted
    *
    * @return success a boolean to denote successful/unsuccessful deletion of project
    */
    public boolean deleteProject(int projectID) throws EmployeeManagementException;
	
   /**
    * Prepares a list of all projects on request
    *
    * @return projectList a list of all projects with their details concatenated
    * as individual strings
    */
    public List<String> getAllProjects() throws EmployeeManagementException;
	
   /**
    * Searches for a project using project ID
    *
    * @param projectID ID value of the project which is the search key
    *
    * @return projectDetails Details of the searched project concatenated together in
    * string format, on successful finding of the project.
    */
    public String searchProjectID(int projectID) throws EmployeeManagementException;
	
   /**
    * Updates all details of the project with the given ID
    *
    * @param projectID ID value of the project whose details are updated
    * @param name New update value for name
    * @param manager New update value for manager
    * @param client New update value for client
    * @param deadline New update value for deadline
    *
    * @return a boolean to specify successful/unsuccessful update of project details
    */
    public boolean updateProjectDetails(int projectID, String name, String manager, String client,
                                        Date deadline) throws EmployeeManagementException;
    
   /**
    * {@inheritdoc}
    */
    public List<String[]> getAssignableEmployees(int projectID) throws EmployeeManagementException;
	
   /**
    * Fetches a list of assigned employees for the project
    *
    * @param projectID id of the project whose assigned employees are fetched
    * @param mode Mode of data retrieval
    *
    * @return A List of employees assigned to the project
    */
    public List<String> getAssignedEmployees(int projectID, int mode) throws EmployeeManagementException;
    
   /**
    * {@inheritdoc}
    */
    public List<String> assignEmployee(int projectID, Set<String> employeeIdSet) throws EmployeeManagementException;
	
   /**
    * Unassigns an employee for the given project
    *
    * @param employeeID id of the employee for whom the project is unassigned
    * @param projectID id of the project from which the employee is unassigned
    *
    * @return success a boolean that says whether the unassignment was successful
    */
    public boolean unassignEmployee(String employeeID, int projectID) throws EmployeeManagementException;    
}