package com.ideas2it.employeemanagement.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.projectmanagement.model.Project;

/**
 * Employee class is a template for employees in an organization
 *
 * @version 1.1   27-04-2021
 *
 * @author Gopal G
 */
public class Employee {
	
    private String name;
    private String id;
    private Date dateOfBirth;
    private double salary;
    private String emailID;
    private boolean isDeleted;
    private List<Address> addressList;
    private Set<Project> projectSet;
	
   /*
    * Default constructor
    */
    public Employee() {
        this.addressList = new ArrayList<Address>();
        this.projectSet = new LinkedHashSet<Project>();
    }
	
   /**
    * User defined constructor to initialize variables
    * without address, project
    */ 
    public Employee (String name, String id, Date dateOfBirth, double salary, String emailID) {
        this.name = name;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
        this.emailID = emailID;
        this.isDeleted = false;
        this.addressList = new ArrayList<Address>();
        this.projectSet = new LinkedHashSet<Project>();
    }
	
   /**
    * User defined constructor to initialize variables
    * with address
    */ 
    public Employee (String name, String id, Date dateOfBirth, double salary, String emailID, List<Address> addresses) {
        this.name = name;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
        this.emailID = emailID;
        this.isDeleted = false;
        this.addressList = new ArrayList<Address>();
        this.projectSet = new LinkedHashSet<Project>();
        addressList.addAll(addresses);
    }
	
   /**
    * User defined constructor to initialize variables
    * with project
    */
    public Employee (String name, String id, Date dateOfBirth, double salary, String emailID, List<Address> addresses, Set<Project> projects) {
        this.name = name;
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
        this.emailID = emailID;
        this.isDeleted = false;
        this.addressList = new ArrayList<Address>();
        this.addressList.addAll(addresses);
        this.projectSet = new LinkedHashSet<Project>();
        this.projectSet.addAll(projects);
    }
    
   /**
    * Sets name attribute with a value
    *
    * @param name value received from user
    */
    public void setName(String name){
        this.name = name;
    }
	
   /**
    * Returns name value
    *
    * @return name Name of the employee
    */
    public String getName() {
        return this.name;
    }
	
   /**
    * Sets ID attribute with a value
    *
    * @param ID value received from user
    */
    public void setId(String employeeID) {
        this.id = employeeID;
    }
	
   /**
    * Returns ID value
    *
    * @return id ID of the employee
    */
    public String getId() {
        return this.id;
    }
	
   /**
    * Sets date of birth attribute with a value
    *
    * @param date of birth value received from user
    */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
	
   /**
    * Returns date of birth value
    *
    * @return dateOfBirth DOB of the employee
    */
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }
	
   /**
    * Sets salary attribute with a value
    *
    * @param salary value received from user
    */
    public void setSalary(double salary) {
        this.salary = salary;
    }
	
   /**
    * Returns salary value
    *
    * @return salary Salary of the employee
    */
    public double getSalary() {
        return this.salary;
    }
	
   /**
    * Sets email ID attribute with a value
    *
    * @param email ID value received from user
    */
    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
	
   /**
    * Returns email ID value
    *
    * @return emailID email ID of the employee
    */
    public String getEmailID() {
        return this.emailID;
    }
	
   /**
    * Sets isDeleted attribute with a value
    *
    * @param isDeleted boolean for deletion status
    */
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
	
   /**
    * Returns isDeleted value
    *
    * @return isDeleted boolean for deletion status
    */
    public boolean getIsDeleted() {
        return this.isDeleted;
    }

   /**
    * Adds an address value to this employee's address list
    *
    * @param address Address of the employee
    */
    public void setAddress(Address address) {
        this.addressList.add(address);
    }
	
   /**
    * Adds address values to this employee's address list
    *
    * @param address Address of the employee
    */
    public void setAddressList(List<Address> addresses) {
        this.addressList.addAll(addresses);
    }
	
   /**
    * Returns list of addresses associated with this employee
    *
    * @return addressList List of addresses
    */
    public List<Address> getAddressList() {
        return this.addressList;
    }
	
   /**
    * Adds a project to this employee's project list
    *
    * @param project Project of the employee
    */
    public void setProject(Project project) {
        this.projectSet.add(project);
    }
	
   /**
    * Adds a list of projects to this employee's project list
    *
    * @param projects Project list of the employee
    */
    public void setProjectSet(Set<Project> projects) {
        if (null == projectSet) {
            this.projectSet = new LinkedHashSet<Project>();
        }
        if (0 < projects.size()) {
            this.projectSet.addAll(projects);
        }
    }
	
   /**
    * Returns list of projects assigned with this employee
    *
    * @return projectList List of projects
    */
    public Set<Project> getProjectSet() {
        return this.projectSet;
    }
	
   /**
    * Checks if two objects of employee type are equal
    *
    * @param employee An instance of employee which is to be checked for parity with this object
    *
    * @return employee attributes
    */
    public boolean equals(Employee employee) {
        boolean success = false;
        success = ((this.id == employee.getId()) && (this.name.equals(employee.getName()))
                   && (this.dateOfBirth == employee.getDateOfBirth()) && (this.emailID.equals(employee.getDateOfBirth()))
                   && (this.salary == employee.getSalary()) && (this.addressList.size() == employee.getAddressList().size())
                   && (this.projectSet.size() == employee.getProjectSet().size()));
        return success;
    }
	
	
   /**
    * Returns a string with employee attribute values concatenated in a formatted
    * structure for output purposes.
    *
    * @return employee attributes
    */
    public String toString() {
        String employeeDetails = ("Employee Details:\n\tName: " + name + "\n\tID: "+ id
                                 + "\n\tDate of birth: " + dateOfBirth
                                 + "\n\tSalary: " + salary + "\n\tEmail ID: "
                                 + emailID + "\n\n\tAddresses:");
        if (addressList.size() > 0) {
		    for (Address address : addressList) {
                if (!address.getIsDeleted()) {
			        employeeDetails += ("\n\n\t" + address.toString());
                }
            }
        } else {
            employeeDetails += ("\n\n\tNo address available");
        }
        return employeeDetails;
    }
}