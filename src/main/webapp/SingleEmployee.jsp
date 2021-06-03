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
<title>Individual Employee</title>
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
    List<String[]> employee = (List<String[]>)request.getAttribute("singleEmployee");
    boolean pageAvailable = false;
    if (null != employee) {
        pageAvailable = true;
    }
%>
<body bgcolor="cyan">
    <div class="heading">
        <h3>INDIVIDUAL EMPLOYEE DETAILS</h3>
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
    <!--  Basic details Table -->
    
    <div class="tableHead">
        <h4>Basic Info</h4>
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
       <% 
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
                    <td align="center">
                        <div class="buttonSpace">
                            <form action="employee?action=editDetails" method="post">
                                <input type="hidden" name="emp_id" value="<%= basicInfo[0] %>">
                                <input type="submit" value="Edit">
                            </form>
                        </div>
                    </td>
                </tr>
              <%
          
       %>
    </table>
    
    <!-- Address Table -->
     <div class="tableHead">
        <h4> Address Details </h4>
    </div>
    
    <% 
             int base = 2;
             String addrMetaData[] = employee.get(1);
             int addressSize = Integer.parseInt(addrMetaData[0]);
             int idx = 0;
             if (0 < addressSize) {
    %>
                <table style="width:50%" align="center" cellspacing="0" cellpadding="2">
                <tr>
                    <th>Index</th>
                    <th>Address type</th>
                    <th>Door no</th>
                    <th>Street</th>
                    <th>Locality</th>
                    <th>Pin code</th>
                    <th>District</th>
                    <th>State</th>
                    <th>Operations</th>
                </tr>
        <% 
                int tid = 0;String oldPerm = "-1";
                for(idx = 0; idx < addressSize; idx++) {
                	String address[] = employee.get(base + idx);
                	if ("true".equals(address[1])) {
            	    	oldPerm = address[0];
            	    }
                }
                for (idx = 0; idx < addressSize; idx++)  {
            	    String address[] = employee.get(base + idx);
            	    String addrType = ("true".equals(address[1])) ? "Permanent" : "Temporary";
            	    if ("false".equals(address[8])) {
                  %>
                        <tr>
                            <td align="left"><%= (++tid) %></td>
                            <td align="left"><%= addrType %></td>
                            <td align="left"><%= address[2] %></td>
                            <td align="left"><%= address[3] %></td>
                            <td align="left"><%= address[4] %></td>
                            <td align="left"><%= address[5] %></td>
                            <td align="left"><%= address[6] %></td>
                            <td align="left"><%= address[7] %></td>
                            <td align="center">
                                <div class="buttonSpace">
                                    <form action="employee?action=editAddress" method="post">
                                        <input type="hidden" name="emp_id" value="<%= basicInfo[0] %>">
                                        <input type="hidden" name="addressId" value="<%= address[0] %>">
                                        <input type="submit" value="Edit">
                                    </form>
                                </div>
                                <% if("false".equals(address[1])) {
                                	%> 
                                	<div class="buttonSpace">
                                        <form action="employee?action=deleteAddress" method="post">
                                            <input type="hidden" name="emp_id" value="<%= basicInfo[0] %>">
                                            <input type="hidden" name="addressId" value="<%= address[0] %>">
                                            <input type="submit" value="Delete">
                                        </form>
                                    </div>
                                    <div class="buttonSpace">
                                        <form action="employee?action=markAsPermanent" method="post">
                                            <input type="hidden" name="emp_id" value="<%= basicInfo[0] %>">
                                            <input type="hidden" name="newPermanent" value="<%= address[0] %>">
                                            <input type="hidden" name="oldPermanent" value="<%= oldPerm %>">
                                            <input type="submit" value="Mark as permanent address">
                                        </form>
                                    </div>
                                <% } %>
                            </td>
                        </tr>
                  <%
            	     }
                 } 
                %> </table> <% 
             }else {
                	 %> 
                	    <div class="tableHead">
                            <h4>No Address Available</h4>
                        </div>
            <%  } %>
            
            
            <!--  Project assignments Table -->
     <div class="tableHead">
        <h4> Project Details </h4>
    </div>
    
    <% 
             base += idx;
             String projMetaData[] = employee.get(base);
             int projectSize = Integer.parseInt(projMetaData[0]);
             if (0 < projectSize) {
    %>
                <table style="width:50%" align="center" cellspacing="0" cellpadding="2">
                <tr>
                    <th>Index</th>
                    <th>Name</th>
                    <th>Manager</th>
                    <th>Client</th>
                    <th>Deadline</th>
                    <th>Operations</th>
                </tr>
        <% 
                int tid = 0;base += 1;
                for (idx = 0; idx < projectSize; idx++)  {
            	    String project[] = employee.get(base + idx);
            	    //String projStatus = ("true".equals(project[5]) ? "Inactive" : "Active");
            	    if ("false".equals(project[5])) {
                  %>
                        <tr>
                            <td align="left"><%= (++tid) %></td>
                            <td align="left"><%= project[1] %></td>
                            <td align="left"><%= project[2] %></td>
                            <td align="left"><%= project[3] %></td>
                            <td align="left"><%= project[4] %></td>
                            <td align="center">
                                <div class="buttonSpace">
                                    <form action="employee?action=unassignProject" method="post">
                                        <input type="hidden" name="emp_id" value="<%= basicInfo[0] %>">
                                        <input type="hidden" name="proj_id" value="<%= project[0] %>">
                                        <input type="submit" value="Unassign">
                                    </form>
                                </div>
                            </td>
                        </tr>
                  <%
            	     }
                 } 
                %> </table> <% 
             }else {
                	 %> 
                	    <div class="tableHead">
                            <h4>No Project Available</h4>
                        </div>
            <%  } %>
            
            <div class="menuSpace">
                <form action="employee?action=addNewAddress" method="post">
                    <input type="hidden" name="emp_id" value="<%= basicInfo[0] %>">
                    <input type="submit" value="Add new address">
                </form>
            </div>
            
            <div class="menuSpace">
                <form action="employee" method="get">
                    <input type="hidden" name="action" value="getAssignableProjects">
                    <input type="hidden" name="emp_id" value="<%= basicInfo[0] %>">
                    <input type="submit" value="Assign Projects">
                </form>
            </div>
            
            <div class="menuSpace">
                <form action="employee?action=deleteEmployee" method="post">
                    <input type="hidden" name="emp_id" value="<%= basicInfo[0] %>">
                    <input type="submit" value="Delete this employee">
                </form>
            </div>
       <% } else {
               request.setAttribute("errorMessage", "Error in displaying employee profile");
               RequestDispatcher rd = request.getRequestDispatcher("/ErrorDisplay.jsp");
               rd.forward(request, response);
       }%>
       </body>
</html>