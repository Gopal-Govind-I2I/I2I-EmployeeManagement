<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.io.PrintWriter" %>

<%@ page import= "javax.servlet.http.HttpServletRequest" %>
<%@ page import= "javax.servlet.http.HttpServletResponse" %>

<%@ page import= "com.ideas2it.employeemanagement.service.impl.EmployeeServiceImpl" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee</title>
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
        margin: 2px;
        padding: 2px;
    }
    table, th, td {
        border: 1px solid black;
    }
</style>
</head>
<%
    String errorMessage = (String)request.getAttribute("errorMsg");
    PrintWriter printWriter = response.getWriter();
    List<List<String[]>> deletedEmployees = (List<List<String[]>>) request.getAttribute("deletedEmployees");
    int listSize = 0;
    if(null != deletedEmployees) {
        listSize = deletedEmployees.size();
    }
%>
<body bgcolor="cyan">
    <div class="heading">
        <h3>INACTIVE EMPLOYEES PORTAL</h3>
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
    
    <%
    
        if(0 < listSize) {
        %>
        	<div class="tableHead">
                <h4>LIST OF INACTIVE EMPLOYEES</h4>
            </div>
            <br><br>
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
                <% for (List<String[]> employee : deletedEmployees) {
                       String basicInfo[] = employee.get(0);
                       String status = ("true".equals(basicInfo[5])) ? "Suspended" : "Active";
                 %>
                       <tr>
                           <td align="left"><%= basicInfo[0] %></td>
                           <td align="left"><%= basicInfo[1] %></td>
                           <td align="left"><%= basicInfo[2] %></td>
                           <td align="left"><%= basicInfo[3] %></td>
                           <td align="left"><%= basicInfo[4] %></td>
                           <td align="left"><%= status %></td>
                           <td align="left">
                              <div class="buttonSpace">
                               <form action="employee?action=restoreEmployee" method="post">
                                   <input type="hidden" name="emp_id" value="<%= basicInfo[0] %>">
                                   <input type="submit" value="Restore">
                               </form>
                              </div>
                           </td>
                       </tr>
                  <%
                }
           %>
        </table>
  <% } else {
            %> 
                <div class="tableHead">
                     <h3> NO EMPLOYEES AVAILABLE NOW</h3>
                </div>
            <%
      }
    
    %>
    
</body>
</html>