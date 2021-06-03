package com.ideas2it.projectmanagement.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.ideas2it.employeemanagement.model.Employee;
import com.ideas2it.employeemanagement.service.impl.EmployeeServiceImpl;
import com.ideas2it.exception.EmployeeManagementException;
import com.ideas2it.projectmanagement.dao.ProjectDao;
import com.ideas2it.projectmanagement.dao.impl.ProjectDaoImpl;
import com.ideas2it.projectmanagement.model.Project;
import com.ideas2it.projectmanagement.service.ProjectService;

/**
 * ProjectServiceImpl performs behind-the-screen service layer operations for
 * Project CRUD operations by implementing ProjectService interface
 *
 * @version 1.0   01-05-2021
 *
 * @author Gopal G
 */

public class ProjectServiceImpl implements ProjectService {
    private ProjectDao projectDao = new ProjectDaoImpl();

   /**
    * {@inheritdoc}
    */
    public Date getDeadline(String deadlineDate) {
        Date deadline;
        try {
            deadline = Date.valueOf(deadlineDate);
        } catch(Exception exception) {
            deadline = null;
        }
        return deadline;
    }
	
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public Project exposeProject(int id) throws EmployeeManagementException {
        Project project = projectDao.fetchIndividualProject(id);
        return project;
    }
	
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public boolean addProject(String name, String manager, String client, Date deadline) throws EmployeeManagementException {
        Project project = new Project(name, manager, client, deadline);
        return projectDao.addProject(project);
    }
	
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public boolean checkProjectID(int projectID, int mode) throws EmployeeManagementException {
        Project project = projectDao.fetchIndividualProject(projectID);
        boolean isValid = false;
        if (null != project) {
            if (1 == mode) {
                isValid = !(project.getIsDeleted());
            } else {
                isValid = project.getIsDeleted();
            }
        }
        return isValid;
    }
	
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public boolean restoreProject(int projectID) throws EmployeeManagementException {
        Project project = projectDao.fetchIndividualProject(projectID);
        project.setIsDeleted(false);
        return projectDao.updateProjectDetails(project);
    }

   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public boolean deleteProject(int projectID) throws EmployeeManagementException {
        Project project = projectDao.fetchIndividualProject(projectID);
        project.setIsDeleted(true);
        project.getAssignedEmployees().clear();
        return projectDao.updateProjectDetails(project);
    }
	
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public List<String> getAllProjects() throws EmployeeManagementException {
        List<Project> allProjects = projectDao.getAllProjects();
        List<String> projectList = new ArrayList<String>();
        String projectDetails = "";
        for( Project project : allProjects) {
            projectDetails += "\tID: " + project.getId();
            projectDetails += "\n\tProject Name: " + project.getName();
            projectDetails += "\n\tManager: " + project.getManager();
            projectDetails += "\n\tClient: " + project.getClient();
            projectDetails += "\n\tDeadline: " + project.getDeadline();
            projectList.add(projectDetails);
            projectDetails = "";
        }
        return projectList;
    }
    
    /**
     * {@inheritdoc}
     * @throws EmployeeManagementException 
     */
     public List<List<String[]>> fetchAllProjects() throws EmployeeManagementException {
    	 List<Project> projects = projectDao.getAllProjects();
    	 List<List<String[]>> allProjects = new ArrayList<List<String[]>>();
    	 for(Project project : projects) {
    		 List<String[]> projectDetails = constructProjectDetails(project);
    		 allProjects.add(projectDetails);
    	 }
    	 return allProjects;
     }
     
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public List<Project> getProjectModelList() throws EmployeeManagementException {
    	List<Project> projects = projectDao.getAllProjects();
    	return projects;
    }
     
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
     public List<List<String[]>> getDeletedProjects() throws EmployeeManagementException {
     	List<List<String[]>> deletedProjects = new ArrayList<List<String[]>>();
     	List<Project> allProjects = projectDao.getDeletedProjects();
     	for(Project project : allProjects) {
            List<String[]> proj = constructProjectDetails(project);
     	    deletedProjects.add(proj);
     	}
     	return deletedProjects;
     }
     
   /**
    * {@inheritdoc}
    */
    public List<String[]> constructProjectDetails(Project project) {
    	List<String[]> projectDetails = new ArrayList<String[]>();
    	String basicInfo[] = new String[6];String employeeMetaData[] = new String[1];
    	basicInfo[0] = "" + project.getId();basicInfo[1] = project.getName();
    	basicInfo[2] = project.getManager();basicInfo[3] = project.getClient();
        basicInfo[4] = "" + project.getDeadline();basicInfo[5] = "" + project.getIsDeleted();
        projectDetails.add(basicInfo);employeeMetaData[0] = "" + project.getAssignedEmployees().size();
        projectDetails.add(employeeMetaData);
        for(Employee employee : project.getAssignedEmployees()) {
        	String emp[] = new String[6];emp[0] = employee.getId();
        	emp[1] = employee.getName();emp[2] = "" + employee.getDateOfBirth();
        	emp[3] = employee.getEmailID();emp[4]= "" + employee.getSalary();
        	emp[5] = "" + employee.getIsDeleted();
        	projectDetails.add(emp);
        }
    	return projectDetails;
    }
     
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public String searchProjectID(int projectID) throws EmployeeManagementException {
        Project project = projectDao.fetchIndividualProject(projectID);
        String projectDetails = "";
        if (null != project) {
            projectDetails += "ID: " + project.getId();
            projectDetails += "\n\tProject Name: " + project.getName();
            projectDetails += "\n\tManager: " + project.getManager();
            projectDetails += "\n\tClient: " + project.getClient();
            projectDetails += "\n\tDeadline: " + project.getDeadline();
        }
        return projectDetails;
    }
    
    /**
     * {@inheritdoc}
     * @throws EmployeeManagementException 
     */
     public List<String[]> searchIndividualProject(int projectID) throws EmployeeManagementException {
     	Project project = projectDao.fetchIndividualProject(projectID);
     	List<String[]> projectDetails = new LinkedList<String[]>();
     	if (null == project) {
            String arr[] = new String[1];arr[0]="NULL";
            projectDetails.add(arr);
     	} else {
     	    projectDetails = constructProjectDetails(project);
     	}
     	return projectDetails;
     }
	
   /**
    * {@inheritdoc}
    * @throws EmployeeManagementException 
    */
    public boolean updateProjectDetails(int projectID, String name, String manager, String client,
                                        Date deadline) throws EmployeeManagementException {
        Project project = projectDao.fetchIndividualProject(projectID);
        String projectName = (("" == name) ? project.getName() : name);
        String projectManager = (("" == manager) ? project.getManager() : manager);
        String projectClient = (("" == client) ? project.getClient() : client);
        Date projectDeadline = ((null == deadline) ? project.getDeadline() : deadline);
        project.setName(projectName);
        project.setManager(projectManager);
        project.setClient(projectClient);
        project.setDeadline(projectDeadline);
        return projectDao.updateProjectDetails(project);
    }
    
    /**
     * {@inheritdoc}
     * @throws EmployeeManagementException 
     */
     public List<String[]> getAssignableEmployees(int projectID) throws EmployeeManagementException {
     	EmployeeServiceImpl employeeServiceImpl = new EmployeeServiceImpl();
     	List<Employee> allEmployees = employeeServiceImpl.getEmployeeModelList();
     	Project project = exposeProject(projectID);
     	Set<String> assignedEmployees = new LinkedHashSet<String>();
     	for(Employee employee : project.getAssignedEmployees()) {
     		assignedEmployees.add(employee.getId());
     	}
     	List<String[]> employeeList = new ArrayList<String[]>();
     	if (0 < assignedEmployees.size()) {
            for(Employee employee : allEmployees) {
                if(!assignedEmployees.contains(employee.getId())) {
                    String details[] = new String[2];
                    details[0] = "" + employee.getId();
                    details[1] = employee.getName();
                    employeeList.add(details);
                }
            }
     	} else {
            for(Employee employee : allEmployees) {
                String details[] = new String[2];
                details[0] = "" + employee.getId();
                details[1] = employee.getName();
                employeeList.add(details);
            }
     	}
     	return employeeList;
     }
	
   /**
    * {@inheritdoc}
 * @throws EmployeeManagementException 
    */
    public List<String> assignEmployee(int projectID, Set<String> employeeIdSet) throws EmployeeManagementException {
        EmployeeServiceImpl employeeServiceImpl = new EmployeeServiceImpl();
        List<String> unassignableIdList = new ArrayList<String>();
        List<String> existingList = new ArrayList<String>();
        List<String> eligibleList = new ArrayList<String>();
        Set<Employee> employees = new LinkedHashSet<Employee>();
        boolean success = false;
        Project project = projectDao.fetchIndividualProject(projectID);
        for (Employee employee : project.getAssignedEmployees()) {
            existingList.add(employee.getId());
        }
        for (String id : employeeIdSet) {
            if (null == employeeServiceImpl.exposeEmployee(id) || existingList.contains(id)) {
                unassignableIdList.add(id);
            } else {
                Employee employee = employeeServiceImpl.exposeEmployee(id);
                if (!employee.getIsDeleted()) {
                    eligibleList.add(id);
                    employees.add(employee);
                } else {
                    unassignableIdList.add(id);
                }
            }
        }
        if (0 < employees.size()) {
            project.setAssignedEmployees(employees);
            success = projectDao.updateProjectDetails(project);
        }
        if (!success) {
            unassignableIdList.addAll(eligibleList);
        }
        return unassignableIdList;
    }
	
   /**
    * {@inheritdoc}
 * @throws EmployeeManagementException 
    */
    public List<String> getAssignedEmployees(int projectID, int mode) throws EmployeeManagementException {
        Project project = projectDao.fetchIndividualProject(projectID);
        Set<Employee> employeeSet = project.getAssignedEmployees();
        List<String> employees = new ArrayList<String>();
        for(Employee employee : employeeSet) {
            if (!employee.getIsDeleted()) {
                employees.add(employee.toString());
            }
        }
        return employees;
    }
	
   /**
    * {@inheritdoc}
 * @throws EmployeeManagementException 
    */
    public boolean unassignEmployee(String employeeID, int projectID) throws EmployeeManagementException {
        EmployeeServiceImpl employeeServiceImpl = new EmployeeServiceImpl();
        Employee employee = null;
        Project project = projectDao.fetchIndividualProject(projectID);
        boolean success = false;
l1:     for(Employee emp : project.getAssignedEmployees()) {
            if (employeeID.equals(emp.getId())) {
                employee = emp;
                break l1;
            }
        }
        success = (null != employee ? project.getAssignedEmployees().remove(employee) : false);
        success = (success ? (success && projectDao.updateProjectDetails(project)) : false);
        return success;
    }
}
