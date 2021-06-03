package com.ideas2it.employeemanagement.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ideas2it.employeemanagement.service.EmployeeService;
import com.ideas2it.employeemanagement.service.impl.EmployeeServiceImpl;
import com.ideas2it.exception.EmployeeManagementException;
import com.ideas2it.logger.EmployeeManagementLogger;

/**
 * 
 * Handles server requests at Employee side by acting as a controller and makes
 * appropriate function calls according to every specific action
 * 
 * @version 1.1 21-05-2021
 * 
 * @author Gopal G
 *
 */
 public class EmployeeController extends HttpServlet {
     EmployeeService employeeService = new EmployeeServiceImpl();
     EmployeeManagementLogger logger = new EmployeeManagementLogger(EmployeeController.class);
     
    /**
     * Fetches details of all employees from service layer for display purposes
     *
     * @param request Instance of HttpServletRequest which has necessary parameters and
     *                attributes required for delivering a web service.
     * @param response Instance of HttpServletResponse which has the necessary response 
     *                that is to be delivered to the web client.
     *                
     */
     public void fetchAllEmployees(HttpServletRequest request, HttpServletResponse response) {
         List<List<String[]>> allEmployees = null;
         boolean errorFlag = false;
         try {
             allEmployees = employeeService.fetchAllEmployees();
         } catch(EmployeeManagementException eme) {
             errorFlag = true;
             logger.logWarn("Cannot fetch employees. Error display page redirection.");
        	 request.setAttribute("errorMessage", eme.getMessage());
         }
         if(!errorFlag) {
       	     request.setAttribute("allEmployeesList", allEmployees);
         }
         String dispatchDestination = (errorFlag ? "/ErrorDisplay.jsp" : "/Employee.jsp");
         RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
         try {
             requestDispatcher.forward(request,  response);
         } catch (ServletException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
     } 
	
    /**
     * Fetches details of a particular employee for update
     *
     * @param request Instance of HttpServletRequest which has necessary parameters and
     *                attributes required for delivering a web service.
     * @param response Instance of HttpServletResponse which has the necessary response 
     *                that is to be delivered to the web client.
     *                
     */
     public void getEmployeeForEdit(HttpServletRequest request, HttpServletResponse response) {
         String action = request.getParameter("action");
         String employeeID = request.getParameter("emp_id");
         List<String[]> employeeDetails = null;
         boolean errorFlag = false;
         try {
             employeeDetails = employeeService.searchIndividualEmployee(employeeID);
         } catch (EmployeeManagementException eme) {
             errorFlag = true;
             logger.logWarn("Cannot prepare edit page now. Error display page redirection.");
             request.setAttribute("errorMessage", eme.getMessage());
         }
         if(!errorFlag) {
             request.setAttribute("employeeDetails", employeeDetails);
             if ("editDetails".equals(action)) {
                 request.setAttribute("updateAction", "basicDetails");
             } else if ("editAddress".equals(action)) {
                 request.setAttribute("updateAction", "address");
             } else if("addNewAddress".equals(action)) {
                 request.setAttribute("updateAction", "addNewAddress");
             }
         }
         String dispatchDestination = (errorFlag ? "/ErrorDisplay.jsp" : "/FormFill.jsp");
         RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
         try {
             requestDispatcher.forward(request, response);
         } catch (ServletException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
     }
	
    /**
     * Initiates input entry for new employee addition request
     *
     * @param request Instance of HttpServletRequest which has necessary parameters and
     *                attributes required for delivering a web service.
     * @param response Instance of HttpServletResponse which has the necessary response 
     *                that is to be delivered to the web client.
     *                
     */
     public void createNewEmployee(HttpServletRequest request, HttpServletResponse response) {
         request.setAttribute("operation", "createEmployee");
         RequestDispatcher requestDispatcher = request.getRequestDispatcher("/FormFill.jsp");
         try {
             requestDispatcher.forward(request, response);
         } catch (ServletException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
      }
     
    /**
     * 
     * @param request Instance of HttpServletRequest which has necessary parameters and
     *                attributes required for delivering a web service.
     * @return address Address details in a String array
     */
     public String[] processAddress(HttpServletRequest request) {
    	 String address[] = new String[8];
    	 String employeeID = request.getParameter("employeeId");
         address[0] = request.getParameter("isPermanent");
         address[1] = request.getParameter("doorNo");
         address[2] = request.getParameter("street");
         address[3] = request.getParameter("locality");
         address[4] = request.getParameter("pincode");
         address[5] = request.getParameter("district");
         address[6] = request.getParameter("state");
         address[7] = employeeID;
    	 return address;
     }
	
     /**
      * Processes received input data and calls an appropriate method to handle it further
      *
      * @param request Instance of HttpServletRequest which has necessary parameters and
      *                attributes required for delivering a web service.
      * @param response Instance of HttpServletResponse which has the necessary response 
      *                that is to be delivered to the web client.
      *                
      */
      public void addNewEmployee(HttpServletRequest request, HttpServletResponse response) {
          String name = request.getParameter("name");
          String employeeID = request.getParameter("employeeId");
          String dob = request.getParameter("dateOfBirth");
          String email = request.getParameter("email");
          Double salary = Double.parseDouble((String)request.getParameter("salary"));
          String address[] = processAddress(request);
          Date dateOfBirth = employeeService.getBirthDate(dob);
          List<String[]> addresses = new LinkedList<String[]>();
          addresses.add(address);
          passEmployeeToService(name, employeeID, salary, dateOfBirth, email, addresses, request, response);
      }
	  
     /**
      * Performs validation on received data and passes the clean data on to service layer
      * for new employee addition.
      *
      * @param name Name of the employee
      * @param employeeID id of the employee
      * @param salary Salary of the employee
      * @param dateOfBirth Birth date of the employee
      * @param email Email ID of the employee
      * @param addresses List of addresses to be set to employee
      *                
      */
      public void passEmployeeToService(String name, String employeeID, Double salary, Date dateOfBirth,
                                        String email, List<String[]> addresses, HttpServletRequest request, 
                                         HttpServletResponse response) {
          boolean success = false;
          String message = "";
          try {
              if (!(employeeService.checkIfIdExists(employeeID)) && (null != dateOfBirth && employeeService.validateEmail(email))) {
                  success = employeeService.addEmployee(name, employeeID, salary, dateOfBirth, email, addresses);
                  message += (success ? "New employee added successfully" : "Error in adding employee to the roster.");
              } else {
            	  logger.logWarn("Cannot insert new employee details. Invalid input");
                  message += "Invalid input. Check if employee id, date of birth and email are valid.";
              }
          } catch (EmployeeManagementException eme) {
              logger.logWarn("Cannot insert new employee details. Error display page redirection.");
              message += (success ? "" : eme.getMessage());
          }
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
      * Processes the to-be-updated employee data, performs validation and passes on the data
      * to service layer for update.
      *
      * @param request Instance of HttpServletRequest which has necessary parameters and
      *                attributes required for delivering a web service.
      * @param response Instance of HttpServletResponse which has the necessary response 
      *                that is to be delivered to the web client.
      *                
      */
      public void updateEmployeeDetails(HttpServletRequest request, HttpServletResponse response) {
          String name = request.getParameter("name");
          String employeeID = request.getParameter("employeeId");
          String dob = request.getParameter("dateOfBirth");
          String email = request.getParameter("email");
          Double salary = Double.parseDouble((String)request.getParameter("salary"));
          Date dateOfBirth = employeeService.getBirthDate(dob);
          boolean success = false;
          String message = "";
          try {
              if(null != dateOfBirth && employeeService.validateEmail(email) ) {
                  success = employeeService.updateEmployeeDetails(employeeID, name, dateOfBirth, salary, email);
                  message += (success ? "Employee details updated successfully" : "Error in updating employee details");
              } else {
                  logger.logWarn("Cannot update employee details. Input valid.");
                  message += "Invalid input. Check if date of birth and email are valid.";
              }
          } catch (EmployeeManagementException eme) {
              logger.logWarn("Cannot update employee. Error display page redirection.");
              message += (success ? "" : eme.getMessage());
          }
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
       * Processes the to-be-updated address information, performs validation and passes on the data
       * to service layer for update.
       *
       * @param request Instance of HttpServletRequest which has necessary parameters and
       *                attributes required for delivering a web service.
       * @param response Instance of HttpServletResponse which has the necessary response 
       *                that is to be delivered to the web client.
       *                
       */
       public void updateEmployeeAddress(HttpServletRequest request, HttpServletResponse response) {
           String employeeID = request.getParameter("employeeId");
           String doorNo = request.getParameter("doorNo");
           int addressID = Integer.parseInt((String)request.getParameter("addressId"));
           String street = request.getParameter("street");
           String locality = request.getParameter("locality");
           String pincode = request.getParameter("pincode");
           String district = request.getParameter("district");
           String state = request.getParameter("state");
           String message = "";
           boolean success = false;
           try {
               success = employeeService.updateExistingAddress(addressID, doorNo, street, locality, pincode, 
                                                                   district, state, employeeID);
           } catch (EmployeeManagementException eme) {
               logger.logWarn("Cannot update address. Error display page redirection.");
               message += (success ? "" : eme.getMessage());
           }
           message += (success ? "Address update successful." : "Address update unsuccessful.");
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
       * Processes the delete employee request by forwarding it to the service layer
       *
       * @param request Instance of HttpServletRequest which has necessary parameters and
       *                attributes required for delivering a web service.
       * @param response Instance of HttpServletResponse which has the necessary response 
       *                that is to be delivered to the web client.
       *                
       */
       public void deleteEmployee(HttpServletRequest request, HttpServletResponse response) {
           String employeeID = request.getParameter("emp_id");
           boolean success = false;
           String message = "";
           try {
               success = employeeService.deleteEmployee(employeeID);
           } catch(EmployeeManagementException eme) {
               logger.logWarn("Cannot delete employee. Error display page redirection.");
               message += (success ? "" : eme.getMessage());
           }
           message += (success ? "Employee deletion successful." : "Employee deletion unsuccessful.") ;
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
       * Fetches the details of all deleted employees for restore user interaction module
       *
       * @param request Instance of HttpServletRequest which has necessary parameters and
       *                attributes required for delivering a web service.
       * @param response Instance of HttpServletResponse which has the necessary response 
       *                that is to be delivered to the web client.
       *                
       */
       public void getDeletedEmployees(HttpServletRequest request, HttpServletResponse response) {
    	   List<List<String[]>> deletedEmployees = null;
           boolean errorFlag = false;
           RequestDispatcher requestDispatcher = null;
           try {
               deletedEmployees = employeeService.getDeletedEmployees();
           } catch (EmployeeManagementException eme) {
               errorFlag = true;
               logger.logWarn("Cannot fetch deleted employees. Error display page redirection.");
               request.setAttribute("errorMessage", eme.getMessage());
           }
           if (!errorFlag) {
               request.setAttribute("deletedEmployees", deletedEmployees);
           }
           String dispatchDestination = (errorFlag ? "/ErrorDisplay.jsp" : "/DeletedEmployees.jsp");
           requestDispatcher = request.getRequestDispatcher(dispatchDestination);
           try {
               requestDispatcher.forward(request, response);
           } catch (ServletException | IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
       }
	
      /**
       * Processes restore employee request by issuing restore call to service layer
       *
       * @param request Instance of HttpServletRequest which has necessary parameters and
       *                attributes required for delivering a web service.
       * @param response Instance of HttpServletResponse which has the necessary response 
       *                that is to be delivered to the web client.
       *                
       */
       public void restoreEmployee(HttpServletRequest request, HttpServletResponse response) {
           String employeeID = request.getParameter("emp_id");
           String message = "";
           boolean success = false;
           try {
               success = employeeService.restoreEmployee(employeeID);
           } catch (EmployeeManagementException eme) {
               logger.logWarn("Cannot restore employee. Error display page redirection.");
               message += (success ? "" : eme.getMessage());
           }
           message += (success ? "Employee restoration successful." : "Employee restoration unsuccessful.");
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
       * Processes the to-be-added address data, performs validation and passes on the data
       * to service layer for associating it with the respective employee.
       *
       * @param request Instance of HttpServletRequest which has necessary parameters and
       *                attributes required for delivering a web service.
       * @param response Instance of HttpServletResponse which has the necessary response 
       *                that is to be delivered to the web client.
       *                
       */
       public void addNewAddress(HttpServletRequest request, HttpServletResponse response) {
           String employeeID = (String)request.getParameter("employeeId");
           //String address[] = new String[7];
           String address[] = processAddress(request);
           String message = "";
           boolean success = false;
           try {
               success = employeeService.addNewAddress(employeeID, address);
           } catch(EmployeeManagementException eme) {
               logger.logWarn("Cannot add new address. Error display page redirection.");
               message += (success ? "" : eme.getMessage());
           }
           message += (success ? "Address addition successful." : "Address addition unsuccessful.");
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
        * Processes the delete address request by making delete call to service layer
        *
        * @param request Instance of HttpServletRequest which has necessary parameters and
        *                attributes required for delivering a web service.
        * @param response Instance of HttpServletResponse which has the necessary response 
        *                that is to be delivered to the web client.
        *                
        */
        public void deleteAddress(HttpServletRequest request, HttpServletResponse response) {
            String employeeID = request.getParameter("emp_id");
            int addressID = Integer.parseInt((String)request.getParameter("addressId"));
            String message = "";
            boolean success = false;
            try {
                success = employeeService.deleteAddress(addressID,  employeeID);
            } catch (EmployeeManagementException eme) {
                message += (success ? "" : eme.getMessage());
            }
            message += (success ? "Address deletion successful." : "Address deletion unsuccessful.");
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
        * Processes permanent address update request by making appropriate call to service layer
        *
        * @param request Instance of HttpServletRequest which has necessary parameters and
        *                attributes required for delivering a web service.
        * @param response Instance of HttpServletResponse which has the necessary response 
        *                that is to be delivered to the web client.
        *                
        */
        public void updatePermanentAddress(HttpServletRequest request, HttpServletResponse response) {
            String employeeID = request.getParameter("emp_id");
            int newPermanent = Integer.parseInt((String)request.getParameter("newPermanent"));
            int oldPermanent = Integer.parseInt((String)request.getParameter("oldPermanent"));
            String message = "";
            boolean success = false;
            try {
                success = employeeService.updatePermanentAddress(oldPermanent, newPermanent, employeeID);
            } catch (EmployeeManagementException eme) {
            	logger.logWarn("Cannot update permanent address. Error display page redirection.");
                message += (success ? "" : eme.getMessage());
            }
            message += (success ? "Permanent address update successful." : "Permanent address update unsuccessful.");
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
        * Processes unassign project request by making appropriate call to service layer
        *
        * @param request Instance of HttpServletRequest which has necessary parameters and
        *                attributes required for delivering a web service.
        * @param response Instance of HttpServletResponse which has the necessary response 
        *                that is to be delivered to the web client.
        *                
        */
        public void unassignProject(HttpServletRequest request, HttpServletResponse response) {
            String employeeID = (String)request.getParameter("emp_id");
            int projectID = Integer.parseInt((String)request.getParameter("proj_id"));
            String message = "";
            boolean success = false;
            try {
                success = employeeService.unassignProject(employeeID, projectID);
            } catch (EmployeeManagementException eme) {
            	logger.logWarn("Cannot unassign project. Error display page redirection.");
                message += (success ? "" : eme.getMessage());
            }
            message += (success ? "Project unassign successful." : "Project unassign unsuccessful.");
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
        * Fetches the details of assignable projects for an employee for assign employee user interaction module
        *
        * @param request Instance of HttpServletRequest which has necessary parameters and
        *                attributes required for delivering a web service.
        * @param response Instance of HttpServletResponse which has the necessary response 
        *                that is to be delivered to the web client.
        *                
        */
        public void getAssignableProjects(HttpServletRequest request, HttpServletResponse response) {
            String employeeID = request.getParameter("emp_id");
            List<String[]> assignableProjects = null;
            String message = "";
            boolean errorFlag = false;
            try {
                assignableProjects = employeeService.getAssignableProjects(employeeID);
            } catch (EmployeeManagementException eme) {
                errorFlag = true;
                logger.logWarn("Cannot fetch assignable projects. Error display page redirection.");
                message = eme.getMessage();
                request.setAttribute("errorMessage", message);
            }
            if (!errorFlag) {
            	request.setAttribute("employeeID", employeeID);
                request.setAttribute("assignableProjects", assignableProjects);
            }
            String dispatchDestination = (errorFlag ? "/ErrorDisplay.jsp" : "/AssignProjects.jsp");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchDestination);
            try {
                requestDispatcher.forward(request, response);
             } catch (ServletException e) {
                // TODO Auto-generated catch block
	            e.printStackTrace();
             } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
             }
        }
	
       /**
        * Receives a list of projects as request, processes it and passes it on to service layer for assignment.
        *
        * @param request Instance of HttpServletRequest which has necessary parameters and
        *                attributes required for delivering a web service.
        * @param response Instance of HttpServletResponse which has the necessary response 
        *                that is to be delivered to the web client.
        *                
        */
        public void assignProjects(HttpServletRequest request, HttpServletResponse response) {
            String projectIdSet[] = request.getParameterValues("projects");
            String employeeID = request.getParameter("emp_id");
            Set<Integer> assignableIdSet = new LinkedHashSet<Integer>();
            List<Integer> unassignableProjects = null;
            boolean success = false;String message = "";
            for(String id : projectIdSet) {
                int projId = Integer.parseInt(id);
                assignableIdSet.add(projId);
            }
            try {
                unassignableProjects = employeeService.assignProject(employeeID, assignableIdSet);
                success = (unassignableProjects.size() < assignableIdSet.size());
            } catch (EmployeeManagementException eme) {
            	logger.logWarn("Cannot assign projects. Error display page redirection.");
            	message += (success ? "" : eme.getMessage());
            }
            message += (success ? ((assignableIdSet.size() - unassignableProjects.size()) + " projects out of "
            		    + assignableIdSet.size() + " projects assigned successfuly") : "Assignment failure");
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
        
        public void fetchSingleEmployee(HttpServletRequest request, HttpServletResponse response) {
        	String employeeID = (String) request.getParameter("emp_id");
        	List<String[]> employeeDetails = null;
            try {
                employeeDetails = employeeService.searchIndividualEmployee(employeeID);
            } catch (EmployeeManagementException eme) {
            	logger.logWarn("Cannot fetch employee. Error display page redirection.");
            }
            RequestDispatcher requestDispatcher = null;
            if (1 == employeeDetails.size()) {
                String arr[] = employeeDetails.get(0);
                if ("NULL".equals(arr[0])) {
                    request.setAttribute("errorMessage", "NO SUCH EMPLOYEE FOUND");
                    requestDispatcher = request.getRequestDispatcher("/ErrorDisplay.jsp");
                }
            } else {
                request.setAttribute("singleEmployee", employeeDetails);
                requestDispatcher = request.getRequestDispatcher("/SingleEmployee.jsp"); 
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
                logger.logWarn("Invalid action. Index page redirection.");
                request.setAttribute("errorMsg", errorMessage);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                try {
                    requestDispatcher.forward(request, response);
                } catch (ServletException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                switch(action) {
                    case "displayAll" :
                        fetchAllEmployees(request, response);
                        break;
                    case "singleEmployee":
                        fetchSingleEmployee(request, response);
                        break;
                    case "getDeletedEmployees":
                        getDeletedEmployees(request, response);
                        break;
                    case "getAssignableProjects":
                        getAssignableProjects(request, response);
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
            String action = (String) request.getParameter("action");
            if (null == action) {
                String errorMessage = "Warning: Invalid action";
                logger.logWarn("Invalid action. Index page redirection");
                request.setAttribute("errorMsg", errorMessage);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                try {
                    requestDispatcher.forward(request, response);
                } catch (ServletException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                switch(action) {
                    case "createEmployee":
                         createNewEmployee(request, response);
                         break;
                    case "editDetails":
                         getEmployeeForEdit(request, response);
                         break;
                    case "editAddress":
                         getEmployeeForEdit(request,response);
                         break;
                    case "addNewEmployee":
                         addNewEmployee(request, response);
                         break;
                    case "updateEmployeeDetails":
                         updateEmployeeDetails(request, response);
                         break;
                    case "updateEmployeeAddress":
                         updateEmployeeAddress(request, response);
                         break;
                    case "deleteEmployee":
                         deleteEmployee(request, response);
                         break;
                    case "restoreEmployee":
                         restoreEmployee(request, response);
                         break;
                    case "addNewAddress":
                         getEmployeeForEdit(request, response);
                         break;
                    case "insertAddress":
                         addNewAddress(request, response);
                         break;
                    case "deleteAddress":
                         deleteAddress(request, response);
                         break;
                    case "markAsPermanent":
                          updatePermanentAddress(request, response);
                          break;
                    case "unassignProject":
                          unassignProject(request, response);
                          break;
                    case "assignProjects":
                          assignProjects(request, response);
                          break;
                    default:
                          break;
                }
            }
        }
}
