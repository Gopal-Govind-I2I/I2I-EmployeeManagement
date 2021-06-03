package com.ideas2it.projectmanagement.model;

/**
 * Project class is a template for projects undertaken by an organization
 *
 * @version 1.1   09-04-2021
 *
 * @author Gopal G
 */

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ideas2it.employeemanagement.model.Employee;

public class Project {
    private int id;
    private String name;
    private String manager;
    private String client;
    private Date deadline;
    private boolean isDeleted;
    private Set<Employee> assignedEmployees;
	
   /*
    * Default constructor
    */
    public Project() {
    }
	
   /**
    * User defined constructor to initialize variables
    */ 
    public Project(int id, String name, String manager, String client, Date deadline, Set<Employee> employees) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.client = client;
        this.deadline = deadline;
        this.isDeleted = false;
        this.assignedEmployees = new LinkedHashSet<Employee>();
        this.assignedEmployees.addAll(employees);
    }
	
   /**
    * User defined constructor to initialize variables without employee list
    */ 
    public Project(int id, String name, String manager, String client, Date deadline) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.client = client;
        this.deadline = deadline;
        this.isDeleted = false;
        this.assignedEmployees = new LinkedHashSet<Employee>();
    }
	
   /**
    * User defined constructor to initialize variables without id, employee list
    */ 
    public Project(String name, String manager, String client, Date deadline) {
        this.name = name;
        this.manager = manager;
        this.client = client;
        this.deadline = deadline;
        this.isDeleted = false;
        this.assignedEmployees = new LinkedHashSet<Employee>();
    }
	
   /**
    * Sets ID attribute with a value
    *
    * @param id ID value which is set to the project
    */
    public void setId(int id) {
        this.id = id;
    }
	
   /**
    * Returns ID value
    *
    * @return id ID attribute of the project
    */
    public int getId() {
        return this.id;
    }
	
   /**
    * Sets name attribute with a value
    *
    * @param name ID value which is set to the project
    */
    public void setName(String name) {
        this.name = name;
    }
	
   /**
    * Returns name value
    *
    * @return name Name attribute of the project
    */
    public String getName() {
        return this.name;
    }
	
   /**
    * Sets manager attribute with a value
    *
    * @param manager Manager value which is set to the project
    */
    public void setManager(String manager) {
        this.manager = manager;
    }
	
   /**
    * Returns manager value
    *
    * @return manager Manager attribute of the project
    */
    public String getManager() {
        return this.manager;
    }
	
   /**
    * Sets client attribute with a value
    *
    * @param client Client value which is set to the project
    */
    public void setClient(String client) {
        this.client = client;
    }
	
   /**
    * Returns client value
    *
    * @return client Client attribute of the project
    */
    public String getClient() {
        return this.client;
    }
	
   /**
    * Sets deadline attribute with a value
    *
    * @param deadline Deadline value which is set to the project
    */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
	
   /**
    * Returns deadline value
    *
    * @return deadline Deadline attribute of the project
    */
    public Date getDeadline() {
        return this.deadline;
    }
	
   /**
    * Sets isDeleted attribute with a value
    *
    * @param isDeleted Deletion status of the project
    */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
	
   /**
    * Returns isDeleted value
    *
    * @return isDeleted Deletion status of the project
    */
    public boolean getIsDeleted() {
        return this.isDeleted;
    }
	
   /**
    * Adds an employee list to the assigned employees list
    *
    * @param employeeList A list of Employee objects which are assigned to this project
    */
    public void setAssignedEmployees(Set<Employee> employeeSet) {
        if (null == this.assignedEmployees) {
            this.assignedEmployees = new LinkedHashSet<Employee>();
        }
        if (0 < employeeSet.size()) {
            this.assignedEmployees.addAll(employeeSet);
        }
    }
	
   /**
    * Returns a list of assigned employees
    *
    * @return assignedEmployees A list of employees assigned to the project
    */
    public Set<Employee> getAssignedEmployees() {
        return this.assignedEmployees;
    }
	
   /**
    * Adds an employee to the assigned employees list
    *
    * @param employee Employee object which is assigned to this project
    */
    public void setEmployee(Employee employee) {
        this.assignedEmployees.add(employee);
    }
	
   /**
    * Project details concatenated together as a string
    *
    * @param projectDetails Project details concatenated together as a single string
    */
    public String toString() {
        String projectDetails;
        projectDetails = ("\n\tProject ID: " + id + "\n\tName: " + name 
                         + "\n\tManager: " + manager + "\n\tClient: " + client 
                         + "\n\tDeadline: " + deadline);
        return projectDetails;
    }
}