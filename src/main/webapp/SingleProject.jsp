<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.io.PrintWriter" %>
<%@ page import= "javax.servlet.http.HttpServletRequest" %>
<%@ page import= "javax.servlet.http.HttpServletResponse" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Single Project</title>
<style>
    .heading {
        border: 5px outset blue;
        background-color: white;
        text-align: center;
    }
    .tableHead {
        text-align: center;
    }
    .alertMessage {
        text-align: center;
        background-color: red;
    }
    .menuSpace {
        background-color: cyan;
        text-align: center;
        margin: 20px;
        padding: 20px;
    }
    .buttonSpace {
        background-color: cyan;
        text-align: center;
        margin: 3px;
        padding: 3px;
    }
    table, th, td {
        border: 1px solid black;
    }
</style>
</head>
<%
    String errorMessage = (String)request.getAttribute("errorMsg");
    //errorMessage = "Alert: Sample error for test.";
    PrintWriter printWriter = response.getWriter();
    List<String[]> project = (List<String[]>)request.getAttribute("singleProject");
    boolean pageAvailable = false;
    if (null != project) {
    	pageAvailable = true;
    }
%>
<body bgcolor="cyan">
    <div class="heading">
        <h3>INDIVIDUAL PROJECT DETAILS</h3>
    </div>
    <%
            if(null != errorMessage) {
        	    %>
        	    <div class="alertMessage">
        	        <p><%= errorMessage %></p>
        	    </div>
         <% }%>
         
       <div class="menuSpace">
           <a href="index.jsp"> 
               <input type="button" value="Main menu" />
           </a>
       </div>

    <br><br>
   
   <% if (pageAvailable) { %>
    <div class="tableHead">
        <h4>Basic Info</h4>
    </div>
    <br><br>
    <table style="width:50%" align="center" cellspacing="0" cellpadding="2">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Manager</th>
            <th>Client</th>
            <th>Deadline</th>
            <th>Status</th>
            <th>Operations</th>
       </tr>
       <% 
              String basicInfo[] = project.get(0);
              String status = ("true".equals(basicInfo[5])) ? "Suspended" : "Active";
              %>
                <tr>
                    <td align="left"><%= basicInfo[0] %></td>
                    <td align="left"><%= basicInfo[1] %></td>
                    <td align="left"><%= basicInfo[2] %></td>
                    <td align="left"><%= basicInfo[3] %></td>
                    <td align="left"><%= basicInfo[4] %></td>
                    <td align="left"><%= status %></td>
                    <td align="center">
                        <div class="buttonSpace">
                            <form action="project?action=editDetails" method="post">
                                <input type="hidden" name="proj_id" value="<%= basicInfo[0] %>">
                                <input type="submit" value="Edit">
                            </form>
                        </div>
                    </td>
                </tr>
              <%
          
       %>
    </table>
    
    <!-- Employee Table -->
     <div class="tableHead">
        <h4> Assigned Employees </h4>
    </div>
    <% 
             int base = 2;
             String employeeMetaData[] = project.get(1);
             int noOfAssignments = Integer.parseInt(employeeMetaData[0]);
             int idx = 0;
             if (0 < noOfAssignments) {
           %>
           <table style="width:50%" align="center" cellspacing="0" cellpadding="2">
               <tr>
                   <th>ID</th>
                   <th>Name</th>
                   <th>Date of Birth</th>
                   <th>Email ID</th>
                   <th>Salary</th>
                   <th>Status</th>
                   <th>Operations</th>
               </tr>
             <%   for (idx = 0; idx < noOfAssignments; idx++)  {
            	    String employee[] = project.get(base + idx);
            	    String state = ("false".equals(employee[5]) ? "Active" : "Inactive");
                  %>
                        <tr>
                            <td align="left"><%= employee[0] %></td>
                            <td align="left"><%= employee[1] %></td>
                            <td align="left"><%= employee[2] %></td>
                            <td align="left"><%= employee[3] %></td>
                            <td align="left"><%= employee[4] %></td>
                            <td align="left"><%= state %></td>
                            <td align="center">
                                <div class="buttonSpace">
                                    <form action="project?action=unassignEmployee" method="post">
                                        <input type="hidden" name="proj_id" value="<%= basicInfo[0] %>">
                                        <input type="hidden" name="emp_id" value="<%= employee[0] %>">
                                        <input type="submit" value="Unassign">
                                    </form>
                                </div>
                            </td>
                        </tr>
                  <%
                 } 
                %> </table> <% 
             } else {
                	 %> 
                	    <div class="tableHead">
                            <h4>No Employee Available</h4>
                        </div>
            <% } %>
            
            <div class="menuSpace">
                <form action="project" method="get">
                    <input type="hidden" name="action" value="getAssignableEmployees">
                    <input type="hidden" name="proj_id" value="<%= basicInfo[0] %>">
                    <input type="submit" value="Assign Employees">
                </form>
            </div>
            
            <div class="menuSpace">
                <form action="project?action=deleteProject" method="post">
                    <input type="hidden" name="proj_id" value="<%= basicInfo[0] %>">
                    <input type="submit" value="Delete this project">
                </form>
            </div>
      <% } else {
            request.setAttribute("errorMessage", "Cannot display project profile now");
            RequestDispatcher rd = request.getRequestDispatcher("/ErrorDisplay.jsp");
            rd.forward(request, response);
      } %>
</body>
</html>