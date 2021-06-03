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
    List<List<String[]>> allEmployees = (List<List<String[]>>) request.getAttribute("allEmployeesList");
    int listSize = 0;
    if(null != allEmployees) {
        listSize = allEmployees.size();
    }
%>
<body bgcolor="cyan">
    <div class="heading">
        <h3>EMPLOYEE VIEW</h3>
    </div>
    <%
            if(null != errorMessage) {
        	    %>
        	    <div class="alertMessage">
        	        <p><%= errorMessage %></p>
        	    </div>
         <% }%>
    <div class="menuSpace">
        <form action="employee?action=createEmployee" method="post">
            <input type="submit" value="Create employee">
        </form>
    </div>
    
    <div class="menuSpace">
        <form action="employee" method="get">
            <input type="hidden" name="action" value="getDeletedEmployees">
            <input type="submit" value="View inactive employees">
        </form>
    </div>
    
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
                <h4>LIST OF ACTIVE EMPLOYEES</h4>
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
                <% for (List<String[]> employee : allEmployees) {
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
                               <form action="employee" method="get">
                                   <input type="hidden" name="action" value="singleEmployee">
                                   <input type="hidden" name="emp_id" value="<%= basicInfo[0] %>">
                                   <input type="submit" value="View full details">
                               </form>
                              </div>
                           </td>
                       </tr>
                  <%
                }
           %>
        </table>
        <div class="menuSpace">
           <form action="employee" method="get">
               <h4>SEARCH INDIVIDUAL EMPLOYEE</h4> <br><br>
                   <input type="hidden" name="action" value="singleEmployee">
               Employee ID : <input type="text" name="emp_id"><br><br>
               <input type="submit" value="Search">
           </form>
        </div> 
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