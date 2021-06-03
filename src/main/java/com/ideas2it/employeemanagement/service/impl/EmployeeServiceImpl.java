package com.ideas2it.employeemanagement.service.impl;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Set;

import com.ideas2it.employeemanagement.dao.EmployeeDao;
import com.ideas2it.employeemanagement.dao.impl.EmployeeDaoImpl;
import com.ideas2it.employeemanagement.model.Address;
import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.service.EmployeeService;
import com.ideas2it.exception.EmployeeManagementException;
import com.ideas2it.projectmanagement.model.Project;
import com.ideas2it.projectmanagement.service.impl.ProjectServiceImpl;

/**
 * EmployeeServiceImpl performs behind-the-screen service layer operations by
 * implementing EmployeeService interface.
 *
 * @version 1.0   29-04-2021
 *
 * @author Gopal G
 */
public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeDao employeeDao = new EmployeeDaoImpl();
	
   /**
    * {@inheritdoc}
    */
    public boolean checkIfIdExists(String id) throws EmployeeManagementException {
        Employee employee = employeeDao.fetchIndividualEmployee(id);
        return (null != employee);
    }
	
   /**
    * {@inheritdoc}
    */
    public Employee exposeEmployee(String id) throws EmployeeManagementException {
        Employee employee = employeeDao.fetchIndividualEmployee(id);
        return employee;
    }

   /**
    * {@inheritdoc}
    */
    public boolean validateEmployeeID(String employeeID, int mode) throws EmployeeManagementException {
        boolean isValid = false;
        Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
        if (null != employee) {
            if (1 == mode) {
                isValid = (!employee.getIsDeleted());
            } else {
                isValid = employee.getIsDeleted();
            }
        }
        return isValid;
    }
	
   /**
    * {@inheritdoc}
    */
    public Date getBirthDate(String birthDate) {
        Date dateOfBirth;
        try {
            dateOfBirth = Date.valueOf(birthDate);
        } catch(Exception exception) {
            dateOfBirth = null;
        }
        return dateOfBirth;
    }
	
   /**
    * {@inheritdoc}
    */
    public boolean validateEmail(String email) {
        String regexPattern = "^[a-zA-Z0-9._]+@[a-zA-Z0-9.]+$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
	
   /**
    * {@inheritdoc}
    */
    public String searchEmployeeID(String employeeID) throws EmployeeManagementException {
       Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
       String employeeDetails = employee.toString();
       return employeeDetails;
    }
    
   /**
    * {@inheritdoc}
    */
    public List<String[]> searchIndividualEmployee(String employeeID) throws EmployeeManagementException {
    	Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
    	List<String[]> employeeDetails = new LinkedList<String[]>();
    	if (null == employee) {
    		String arr[] = new String[1];arr[0]="NULL";
    		employeeDetails.add(arr);
    	} else {
    	    employeeDetails = constructEmployeeDetails(employee);
    	}
    	return employeeDetails;
    }
	
   /**
    * {@inheritdoc}
    */
    public boolean deleteEmployee(String employeeID) throws EmployeeManagementException{
        /*boolean success = employeeDao.deleteEmployee(employeeID);*/
        Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
        boolean success = false;
        if (null != employee && !(employee.getIsDeleted())) {
            employee.setIsDeleted(true);
            List<Address> addresses = employee.getAddressList();
            for (Address address : addresses ) {
                address.setIsDeleted(true);
            }
            employee.getProjectSet().clear();
            success = employeeDao.updateEmployeeDetails(employee);
        }
        return success;
    }
	
   /**
    * {@inheritdoc}
    */
    public boolean restoreEmployee(String employeeID) throws EmployeeManagementException {
        Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
        boolean success = false;
        if (null != employee && (employee.getIsDeleted())) {
            employee.setIsDeleted(false);
            List<Address> addresses = employee.getAddressList();
            for (Address address : addresses ) {
                address.setIsDeleted(false);
            }
            success = employeeDao.updateEmployeeDetails(employee);
        }
        return success;
    }
	
   /**
    * {@inheritdoc}
    */
    public List<String> getAllEmployees() throws EmployeeManagementException {
        List<String> employeesList = new ArrayList<String>();
        List<Employee> allEmployees = employeeDao.getAllEmployees();
        for (Employee employee : allEmployees) {
            employeesList.add(employee.toString());
        }
        return employeesList;
    }
    
   /**
    * {@inheritdoc}
    */
    public List<List<String[]>> getDeletedEmployees() throws EmployeeManagementException {
    	List<List<String[]>> deletedEmployees = new ArrayList<List<String[]>>();
    	List<Employee> allEmployees = employeeDao.getDeletedEmployees();
    	for(Employee employee : allEmployees) {
    			List<String[]> emp = constructEmployeeDetails(employee);
    			deletedEmployees.add(emp);
    	}
    	return deletedEmployees;
    }
    
    /**
     * {@inheritdoc}
     */
     public List<List<String[]>> fetchAllEmployees() throws EmployeeManagementException{
         List<List<String[]>> employeesList = new ArrayList<List<String[]>>();
         List<Employee> allEmployees = employeeDao.getAllEmployees();
         for (Employee employee : allEmployees) {
        	 List<String[]> employeeDetails = constructEmployeeDetails(employee);
             employeesList.add(employeeDetails);
         }
         return employeesList;
     }
     
     /**
      * {@inheritdoc}
      */
      public List<Employee> getEmployeeModelList() throws EmployeeManagementException {
      	List<Employee> employees = employeeDao.getAllEmployees();
      	return employees;
      }
     
   /**
    * {@inheritdoc}
    */
    public List<String[]> constructEmployeeDetails(Employee employee) {
    	List<String[]> employeeDetails = new ArrayList<String[]>();
    	String basicInfo[] = new String[6];
    	String addressMetaData[] = new String[1];
    	String projectMetaData[] =new String[1];
    	basicInfo[0] = employee.getId();basicInfo[1] = employee.getName();basicInfo[2]= "" + employee.getDateOfBirth();
    	basicInfo[3] = employee.getEmailID(); basicInfo[4] = "" + employee.getSalary();basicInfo[5] = "" + employee.getIsDeleted();
    	employeeDetails.add(basicInfo);
    	addressMetaData[0] = "" + 0;int addrCount = 0;int projCount = 0;
    	for (Address address : employee.getAddressList()) {
    		if(!address.getIsDeleted()) {
    			String addr[] =  new String[9];
        		addr[0] = "" + address.getId(); addr[1] = "" + address.getIsPermanent(); addr[2] = address.getDoorNumber();
        		addr[3] = address.getStreet(); addr[4] = address.getLocality(); addr[5] = address.getPincode();
        		addr[6] = address.getDistrict(); addr[7] = address.getState(); addr[8] = "" + address.getIsDeleted();
        		employeeDetails.add(addr);addrCount++;
    		}
    	}
    	addressMetaData[0] = "" + addrCount;
    	employeeDetails.add(1, addressMetaData);
    	projectMetaData[0] = "" + employee.getProjectSet().size();
    	//System.out.print("\n\n\t\t" + basicInfo[0] + "--" + addressMetaData[0] + "--" + projectMetaData[0]);
    	employeeDetails.add(projectMetaData);
    	for (Project project : employee.getProjectSet()) {
    		String proj[] = new String[6];
    		proj[0] = "" + project.getId(); proj[1] = project.getName(); proj[2] = project.getManager();
    		proj[3] = project.getClient(); proj[4] = "" + project.getDeadline(); proj[5] = "" + project.getIsDeleted();
    		employeeDetails.add(proj);
    	}
    	return employeeDetails;
    }
	
   /**
    * {@inheritdoc}
    */
    public boolean updateEmployeeDetails(String employeeID, String employeeName, Date employeeDateOfBirth, 
                                       double employeeSalary, String employeeEmailID) throws EmployeeManagementException{
        Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
        String name = ("".equals(employeeName) ? employee.getName() : employeeName);
        Date dateOfBirth = (null == employeeDateOfBirth ? employee.getDateOfBirth() : employeeDateOfBirth);
        double salary = (0 == employeeSalary ? employee.getSalary() : employeeSalary);
        String emailID = ("".equals(employeeEmailID) ? employee.getEmailID() : employeeEmailID);
        employee.setName(name);
        employee.setDateOfBirth(dateOfBirth);
        employee.setSalary(salary);
        employee.setEmailID(emailID);
        boolean success = employeeDao.updateEmployeeDetails(employee);
        return success;
    }
	
   /**
    * {@inheritdoc}
    */
    public boolean updateExistingAddress(int addressID, String doorNumber, String street,
                                         String locality, String pincode, String district,
                                         String state, String employeeID) throws EmployeeManagementException {
        Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
        List<Address> addresses = employee.getAddressList();
l1:     for (Address address : addresses) {
            if (addressID == address.getId()) {
                address.setDoorNumber(doorNumber);
                address.setStreet(street);
                address.setLocality(locality);
                address.setPincode(pincode);
                address.setDistrict(district);
                address.setState(state);
                break l1;
            }
        }
        return employeeDao.updateEmployeeDetails(employee);
    }
	
   /**
    * {@inheritdoc}
    */
    public boolean deleteAddress(int addressID, String employeeID) throws EmployeeManagementException {
        Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
        List<Address> addresses = employee.getAddressList();
l2:     for (Address address : addresses) {
            if (addressID == address.getId()) {
                address.setIsDeleted(true);
                break l2;
            }
        }
        return employeeDao.updateEmployeeDetails(employee);
    }
	
   /**
    * {@inheritdoc}
    */
    public boolean updatePermanentAddress(int oldPermanentAddress, int newPermanentAddress, String employeeID) throws EmployeeManagementException {
        Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
        boolean revert1, revert2;
        revert1 = revert2 = false;
        List<Address> addresses = employee.getAddressList();
l3:     for (Address address : addresses) {
            if (oldPermanentAddress == address.getId()) {
                address.setIsPermanent(false);
                revert1 = true;
            }
            if (newPermanentAddress == address.getId()) {
                address.setIsPermanent(true);
                revert2 = true;
            }
            if (revert1 && revert2) {
                break l3;
            }
        }
        boolean success = ((revert1 && revert2) && employeeDao.updateEmployeeDetails(employee));
        return success;
    }
	
   /**
    * {@inheritdoc}
    */
    public boolean addNewAddress(String employeeID, String[] addressDetails) throws EmployeeManagementException {
        Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
        boolean isPermanentAddress = ("true".equals(addressDetails[0]));
        Address address = new Address(0, isPermanentAddress, employee, addressDetails[1], addressDetails[2],
                                      addressDetails[3], addressDetails[4], addressDetails[5],
                                      addressDetails[6]);
        employee.setAddress(address);
        return employeeDao.updateEmployeeDetails(employee);
    }
	
   /**
    * {@inheritdoc}
    */
    public List<String[]> getEmployeeAddressList(String employeeID) throws EmployeeManagementException {
        Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
        List<Address> listOfAddresses = employee.getAddressList();
        List<String[]> addressList = new LinkedList<String[]>();
        for (Address address : listOfAddresses) {
            if (!address.getIsDeleted()) {
                String addressDetails[] = new String[8];
                addressDetails[0] = "" + address.getId();
                addressDetails[1] = "" + address.getIsPermanent();
                addressDetails[2] = address.getDoorNumber();
                addressDetails[3] = address.getStreet();
                addressDetails[4] = address.getLocality();
                addressDetails[5] = address.getPincode();
                addressDetails[6] = address.getDistrict();
                addressDetails[7] = address.getState();
                addressList.add(addressDetails);
            }
        }
        return addressList;
    }
	
   /**
    * {@inheritdoc}
    */
    public boolean addEmployee(String name, String employeeID, double salary,
                              Date dateOfBirth, String email, List<String[]> addresses) throws EmployeeManagementException {
        boolean success = false;
        List<Address> allAddresses = new ArrayList<Address>();
        boolean isPermanentAddress = false;
        for (String[] addressArray : addresses) {
            isPermanentAddress = ("true".equals(addressArray[0]));
            Address address = new Address(isPermanentAddress, addressArray[1],
                                          addressArray[2], addressArray[3], addressArray[4],
                                          addressArray[5], addressArray[6]);
            allAddresses.add(address);
        }
        Employee employee = new Employee(name, employeeID, dateOfBirth, salary, email, allAddresses);
        success = employeeDao.addEmployee(employee);
        return success;
    }
    
   /**
    * {@inheritdoc}
    */
    public List<String[]> getAssignableProjects(String employeeID) throws EmployeeManagementException {
    	ProjectServiceImpl projectServiceImpl = new ProjectServiceImpl();
    	List<Project> allProjects = projectServiceImpl.getProjectModelList();
    	Employee employee = exposeEmployee(employeeID);
    	Set<Integer> assignedProjects = new LinkedHashSet<Integer>();
    	for(Project project : employee.getProjectSet()) {
    		assignedProjects.add(project.getId());
    	}
    	List<String[]> projectList = new ArrayList<String[]>();
    	if (0 < assignedProjects.size()) {
    		for(Project project : allProjects) {
        		if(!assignedProjects.contains(project.getId())) {
        			String details[] = new String[2];
            		details[0] = "" + project.getId();
            		details[1] = project.getName();
            		projectList.add(details);
        		}
        	}
    	} else {
    		for(Project project : allProjects) {
        			String details[] = new String[2];
            		details[0] = "" + project.getId();
            		details[1] = project.getName();
            		projectList.add(details);
        	}
    	}
    	return projectList;
    }
	
   /**
    * {@inheritdoc}
    */
    public List<Integer> assignProject(String employeeID, Set<Integer> projectIdSet) throws EmployeeManagementException{
        ProjectServiceImpl projectServiceImpl = new ProjectServiceImpl();
        Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
        List<Integer> unassignableIdList = new ArrayList<Integer>();
        List<Integer> existingList = new ArrayList<Integer>();
        List<Integer> eligibleList = new ArrayList<Integer>();
        Set<Project> projects = new LinkedHashSet<Project>();
        boolean success = false;
        for (Project project : employee.getProjectSet()) {
            existingList.add(project.getId());
        }
        for (Integer id : projectIdSet){
            if (null == projectServiceImpl.exposeProject(id) || existingList.contains(id)) {
                unassignableIdList.add(id);
            } else {
                Project project = projectServiceImpl.exposeProject(id);
                if (!project.getIsDeleted()) {
                    eligibleList.add(id);
                    projects.add(project);
                } else {
                    unassignableIdList.add(id);
                }
            }
        }
        if (0 < projects.size()) {
            employee.setProjectSet(projects);
            success = employeeDao.updateEmployeeDetails(employee);
        }
        if (!success) {
            unassignableIdList.addAll(eligibleList);
        }
        return unassignableIdList;
    }
	
   /**
    * {@inheritdoc}
    */
    public List<String> getAssignedProjects(String employeeID) throws EmployeeManagementException {
        Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
        List<String> assignedProjects = new ArrayList<String>();
        for (Project project : employee.getProjectSet()) {
            if (!project.getIsDeleted()) {
                assignedProjects.add(project.toString());
            }
        }
        return assignedProjects;
    }
    
   /**
    * {@inheritdoc}
    */
    public boolean unassignProject(String employeeID, int projectID) throws EmployeeManagementException {
       Employee employee = employeeDao.fetchIndividualEmployee(employeeID);
       Project projectObj = null;
       boolean success = false;
       List<String> assignedIds = new ArrayList<String>();
l2:     for (Project project : employee.getProjectSet()) {
            if (projectID == project.getId()) {
                projectObj = project;
                break l2;
            }
       }
       if (null != projectObj) {
            success = employee.getProjectSet().remove(projectObj);
            success = (success && employeeDao.updateEmployeeDetails(employee));
       }
       return success;
    }
}