package com.ideas2it.projectmanagement.controller;

import java.io.IOException;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ideas2it.exception.EmployeeManagementException;
import com.ideas2it.projectmanagement.service.ProjectService;
import com.ideas2it.projectmanagement.service.impl.ProjectServiceImpl;
import com.ideas2it.logger.EmployeeManagementLogger;


/**
 * 
 * Handles server requests at Project side by acting as a controller and makes
 * appropriate function calls according to every specific action
 * 
 * @version 1.1 24-05-2021
 * 
 * @author Gopal G
 *
 */
public class ProjectController extends HttpServlet {
    ProjectService projectService = new ProjectServiceImpl();
    EmployeeManagementLogger logger = new EmployeeManagementLogger(ProjectController.class);
	
   /**
    * Fetches details of all projects from service layer for display purposes
    *
    * @param request Instance of HttpServletRequest which has necessary parameters and
    *                attributes required for delivering a web service.
    * @param response Instance of HttpServletResponse which has the necessary response 
    *                that is to be delivered to the web client.
    *                
    */
    public void fetchAllProjects(HttpServletRequest request, HttpServletResponse response) {
        List<List<String[]>> allProjects = null;
        boolean errorFlag = false;String message = "";
        try {
            allProjects = projectService.fetchAllProjects();
        } catch (EmployeeManagementException eme) {
            errorFlag = true;
            message += eme.getMessage();
            request.setAttribute("errorMessage", message);
            logger.logWarn("Cannot fetch projects. Error display page redirection.");
        }
        if (!errorFlag) {
            request.setAttribute("allProjectsList", allProjects);
        }
        String dispatchDestination = (errorFlag ? "/ErrorDisplay.jsp" : "/Project.jsp");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
   /**
    * Fetches details of a particular project for update
    *
    * @param request Instance of HttpServletRequest which has necessary parameters and
    *                attributes required for delivering a web service.
    * @param response Instance of HttpServletResponse which has the necessary response 
    *                that is to be delivered to the web client.
    *                
    */
    public void getProjectForEdit(HttpServletRequest request, HttpServletResponse response) {
        int projectID = Integer.parseInt((String) request.getParameter("proj_id"));
        List<String[]> projectDetails = null;
        boolean errorFlag = false;
        try {
            projectDetails = projectService.searchIndividualProject(projectID);
        } catch (EmployeeManagementException eme) {
            errorFlag = true;
            request.setAttribute("errorMessage", eme.getMessage());
            logger.logWarn("Cannot prepare edit project form. Error display page redirection.");
        }
        if (!errorFlag) {
            request.setAttribute("projectDetails", projectDetails);
        }
        String dispatchDestination = (errorFlag ? "/ErrorDisplay.jsp" : "/ProjectForm.jsp");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
   /**
    * Processes the to-be-updated project data, performs validation and passes on the data
    * to service layer for update.
    *
    * @param request Instance of HttpServletRequest which has necessary parameters and
    *                attributes required for delivering a web service.
    * @param response Instance of HttpServletResponse which has the necessary response 
    *                that is to be delivered to the web client.
    *                
    */
    public void updateProjectDetails(HttpServletRequest request, HttpServletResponse response) {
        int projectID = Integer.parseInt((String)request.getParameter("proj_id"));
        Date deadline = null;
        String name = request.getParameter("name");
        String manager = request.getParameter("manager");
        String client = request.getParameter("client");
        String deadlineDate = request.getParameter("deadline");
        deadline = projectService.getDeadline(deadlineDate);
        boolean success = false;
        String message = "";
        if (null != deadline) {
            try {
                success = projectService.updateProjectDetails(projectID, name, manager, client, deadline);
            } catch (EmployeeManagementException eme) {
                message += (success ? "" : eme.getMessage());
                logger.logWarn("Cannot update project. Error display page redirection.");
            }
        } else {
            message += "Invalid date input for deadline. ";
            logger.logWarn("Cannot fetch projects. Invalid input.");
        }
        message += (success ? "Project update successful." : " Project update unsuccessful.");
        String attributeName = (success ? "successMessage" : "errorMessage");
        String dispatchDestination = (success ? "/SuccessDisplay.jsp" : "/ErrorDisplay.jsp");
        request.setAttribute(attributeName, message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
   /**
    * Initiates input entry for new project addition request
    *
    * @param request Instance of HttpServletRequest which has necessary parameters and
    *                attributes required for delivering a web service.
    * @param response Instance of HttpServletResponse which has the necessary response 
    *                that is to be delivered to the web client.
    *                
    */
    public void createNewProject(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("operation", "createProject");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ProjectForm.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
   /**
    * Processes received input data, performs validation and passes the clean data on to service layer
    * for new project addition.
    *
    * @param request Instance of HttpServletRequest which has necessary parameters and
    *                attributes required for delivering a web service.
    * @param response Instance of HttpServletResponse which has the necessary response 
    *                that is to be delivered to the web client.
    *                
    */
    public void addNewProject(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String manager = request.getParameter("manager");
        String client = request.getParameter("client");
        String deadlineDate = request.getParameter("deadline");
        Date deadline = projectService.getDeadline(deadlineDate);
        String message = (null != deadline ? "" : "Invalid input date for deadline. ") ;
        boolean success = false;
        try {
            success = (null != deadline ? projectService.addProject(name, manager, client, deadline) : false);
        } catch (EmployeeManagementException eme) {
            message += (success ? "" : eme.getMessage());
            logger.logWarn("Cannot add project. Error display page redirection.");
        }
        message += (success ? "Project creation successful." : " Project creation unsuccessful.");
        String attributeName = (success ? "successMessage" : "errorMessage");
        String dispatchDestination = (success ? "/SuccessDisplay.jsp" : "/ErrorDisplay.jsp");
        request.setAttribute(attributeName, message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
        try {
            requestDispatcher.forward(request, response);
         } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
    }
	
   /**
    * Processes the delete project request by forwarding it to the service layer
    *
    * @param request Instance of HttpServletRequest which has necessary parameters and
    *                attributes required for delivering a web service.
    * @param response Instance of HttpServletResponse which has the necessary response 
    *                that is to be delivered to the web client.
    *                
    */
    public void deleteProject(HttpServletRequest request, HttpServletResponse response) {
        int projectID = Integer.parseInt((String)request.getParameter("proj_id"));
        boolean success = false;String message = "";
        try {
            success = projectService.deleteProject(projectID);
        } catch (EmployeeManagementException eme) {
            message += (success ? "" : eme.getMessage());
            logger.logWarn("Cannot delete project. Error display page redirection.");
        }
        message += (success ? "Project deletion successful." : "Project deletion unsuccessful.");
        String attributeName = (success ? "successMessage" : "errorMessage");
        String dispatchDestination = (success ? "/SuccessDisplay.jsp" : "/ErrorDisplay.jsp");
        request.setAttribute(attributeName, message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
   /**
    * Fetches the details of all deleted projects for restore user interaction module
    *
    * @param request Instance of HttpServletRequest which has necessary parameters and
    *                attributes required for delivering a web service.
    * @param response Instance of HttpServletResponse which has the necessary response 
    *                that is to be delivered to the web client.
    *                
    */
    public void getDeletedProjects(HttpServletRequest request, HttpServletResponse response) {
        List<List<String[]>> deletedProjects = null;
        boolean errorFlag = false;String message = "";
        try {
            deletedProjects = projectService.getDeletedProjects();
        } catch (EmployeeManagementException eme) {
            errorFlag = true;
            message += eme.getMessage();
            request.setAttribute("errorMessage", message);
            logger.logWarn("Cannot get deleted projects. Error display page redirection.");
        }
        if (!errorFlag) {
            request.setAttribute("deletedProjects", deletedProjects);
        }
        String dispatchDestination = (errorFlag ? "/ErrorDisplay.jsp" : "/DeletedProjects.jsp");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
   /**
    * Processes restore project request by issuing restore call to service layer
    *
    * @param request Instance of HttpServletRequest which has necessary parameters and
    *                attributes required for delivering a web service.
    * @param response Instance of HttpServletResponse which has the necessary response 
    *                that is to be delivered to the web client.
    *                
    */
     public void restoreProject(HttpServletRequest request, HttpServletResponse response) {
         int projectID = Integer.parseInt((String)request.getParameter("proj_id"));
         boolean success = false;
         String message = "";
         try {
             success = projectService.restoreProject(projectID);
         } catch (EmployeeManagementException eme) {
             message += (success ? "" : eme.getMessage());
             logger.logWarn("Cannot restore project. Error display page redirection.");
         }
         message += (success ? "Project restoration successful." : "Project restoration unsuccessful.");
         String attributeName = (success ? "successMessage" : "errorMessage");
         String dispatchDestination = (success ? "/SuccessDisplay.jsp" : "/ErrorDisplay.jsp");
         request.setAttribute(attributeName, message);
         RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
         try {
             requestDispatcher.forward(request, response);
         } catch (ServletException | IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
     }
	
    /**
     * Processes unassign employee request by making appropriate call to service layer
     *
     * @param request Instance of HttpServletRequest which has necessary parameters and
     *                attributes required for delivering a web service.
     * @param response Instance of HttpServletResponse which has the necessary response 
     *                that is to be delivered to the web client.
     *                
     */
     public void unassignEmployee(HttpServletRequest request, HttpServletResponse response) {
         String employeeID = (String)request.getParameter("emp_id");
         int projectID = Integer.parseInt((String)request.getParameter("proj_id"));
         boolean success = false;String message = "";
         try {
             success = projectService.unassignEmployee(employeeID, projectID);
         } catch (EmployeeManagementException eme) {
             message += (success ? "" : eme.getMessage());
             logger.logWarn("Cannot unassign employee. Error display page redirection.");
         }
         message += (success ? "Employee unassign successful." : "Employee unassign unsuccessful.");
         String attributeName = (success ? "successMessage" : "errorMessage");
         String dispatchDestination = (success ? "/SuccessDisplay.jsp" : "/ErrorDisplay.jsp");
         request.setAttribute(attributeName, message);
         RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
         try {
             requestDispatcher.forward(request, response);
         } catch (ServletException | IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
     }
	
    /**
     * Fetches the details of assignable employees for a project for assign employee user interaction module
     *
     * @param request Instance of HttpServletRequest which has necessary parameters and
     *                attributes required for delivering a web service.
     * @param response Instance of HttpServletResponse which has the necessary response 
     *                that is to be delivered to the web client.
     *                
     */
     public void getAssignableEmployees(HttpServletRequest request, HttpServletResponse response) {
         int projectID = Integer.parseInt((String)request.getParameter("proj_id"));
         List<String[]> assignableEmployees = null;
         boolean errorFlag = false;
         try {
             assignableEmployees = projectService.getAssignableEmployees(projectID);
         } catch (EmployeeManagementException eme) {
             errorFlag = true;
             request.setAttribute("errorMessage", eme.getMessage());
             logger.logWarn("Cannot get assignable employees. Error display page redirection.");
         }
         if (!errorFlag) {
              request.setAttribute("projectID", projectID);
              request.setAttribute("assignableEmployees", assignableEmployees);
         }
         String dispatchDestination = (errorFlag ? "/ErrorDisplay.jsp" : "/AssignEmployees.jsp");
         RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
         try {
             requestDispatcher.forward(request, response);
         } catch (ServletException | IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
     }
	
    /**
     * Receives a list of employees as request, processes it and passes it on to service layer for assignment.
     *
     * @param request Instance of HttpServletRequest which has necessary parameters and
     *                attributes required for delivering a web service.
     * @param response Instance of HttpServletResponse which has the necessary response 
     *                that is to be delivered to the web client.
     *                
     */
     public void assignEmployees(HttpServletRequest request, HttpServletResponse response) {
         String employeeIdSet[] = request.getParameterValues("employees");
         int projectID = Integer.parseInt(request.getParameter("proj_id"));
         List<String> unassignableEmployees = null;String message = "";boolean success = false;
         Set<String> assignableIdSet = new LinkedHashSet<String>();
         for(String id : employeeIdSet) {
             assignableIdSet.add(id);
         }
         try {
             unassignableEmployees = projectService.assignEmployee(projectID, assignableIdSet);
             success = (unassignableEmployees.size() < assignableIdSet.size());
         } catch (EmployeeManagementException eme) {
             logger.logWarn("Cannot assign employees. Error display page redirection.");
         }
         message += (success ? ("" + (assignableIdSet.size() - unassignableEmployees.size()) + " out of " 
                                + assignableIdSet.size() + " employees successfully assigned.") : "Assignment failure");
         String attributeName = (success ? "successMessage" : "errorMessage");
         String dispatchDestination = (success ? "/SuccessDisplay.jsp" : "/ErrorDisplay.jsp");
          request.setAttribute(attributeName, message);
          RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
          try {
              requestDispatcher.forward(request, response);
          } catch (ServletException | IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
     }
	
     public void fetchSingleProject(HttpServletRequest request, HttpServletResponse response) {
         boolean invalidInput = false;
         int projectID = 0;
         try {
             projectID = Integer.parseInt((String) request.getParameter("proj_id"));
         } catch (NumberFormatException nfe) {
             invalidInput = true;
         }
         List<String[]> projectDetails = null;
         try {
             projectDetails = projectService.searchIndividualProject(projectID);
         } catch (EmployeeManagementException eme) {
             logger.logWarn("Cannot fetch project. Error display page redirection.");
         }
         RequestDispatcher requestDispatcher = null;
         if (null != projectDetails && 1 == projectDetails.size()) {
             String arr[] = projectDetails.get(0);
	     String message = (invalidInput ? "Invalid input warning!!!" : "NO SUCH PROJECT FOUND");
             if ("NULL".equals(arr[0])) {
                 request.setAttribute("errorMessage", message);
                 requestDispatcher = request.getRequestDispatcher("/ErrorDisplay.jsp");
                 logger.logWarn("Cannot fetch project. No such project exists.");
             }
         } else {
             request.setAttribute("singleProject", projectDetails);
             requestDispatcher = request.getRequestDispatcher("/SingleProject.jsp");
         }
         try {
             requestDispatcher.forward(request, response);
         } catch (ServletException | IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
     }
	
    /**
     * Receives all incoming server requests with get method and delegates calls according to
     * every specific action.
     *
     * @param request Instance of HttpServletRequest which has necessary parameters and
     *                attributes required for delivering a web service.
     * @param response Instance of HttpServletResponse which has the necessary response 
     *                that is to be delivered to the web client.
     *                
     */
     public void doGet(HttpServletRequest request, HttpServletResponse response) {
         String action = request.getParameter("action");
         if (null == action) {
             String errorMessage = "Warning: Invalid action";
             request.setAttribute("errorMsg", errorMessage);
             RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
             logger.logWarn("Invalid action. Index page redirection.");
             try {
                 requestDispatcher.forward(request, response);
             } catch (ServletException | IOException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
         } else {
             switch(action) {
                 case "displayAll":
                     fetchAllProjects(request, response);
                     break;
                 case "singleProject":
                     fetchSingleProject(request, response);
                     break;
                 case "getDeletedProjects":
                     getDeletedProjects(request, response);
                     break;
                 case "getAssignableEmployees":
                     getAssignableEmployees(request, response);
                     break;
                 default:
                     break;
             }	
         }
     }
	
    /**
     * Receives all incoming server requests with post method and delegates calls according to
     * every specific action.
	 *
	 * @param request Instance of HttpServletRequest which has necessary parameters and
	 *                attributes required for delivering a web service.
	 * @param response Instance of HttpServletResponse which has the necessary response 
	 *                that is to be delivered to the web client.
	 *                
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String action = (String)request.getParameter("action");
		if (null == action) {
                    String errorMessage = "Warning: Invalid action";
                    request.setAttribute("errorMsg", errorMessage);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                    logger.logWarn("Invalid action. Index page redirection.");
                    try {
                        requestDispatcher.forward(request, response);
                    } catch (ServletException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                 } else {
                    switch(action) {
                        case "editDetails":
                            getProjectForEdit(request, response);
                            break;
                        case "updateProject":
                            updateProjectDetails(request, response);
                            break;
                        case "createProject":
                            createNewProject(request, response);
                            break;
                        case "addNewProject":
                            addNewProject(request, response);
                            break;
                        case "deleteProject":
                            deleteProject(request, response);
                            break;
                        case "restoreProject":
                            restoreProject(request, response);
                            break;
                        case "unassignEmployee":
			    unassignEmployee(request, response);
                            break;
                        case "assignEmployees":
                            assignEmployees(request, response);
                            break;
                        default:
                            break;
                    }
                 }
     }

}
