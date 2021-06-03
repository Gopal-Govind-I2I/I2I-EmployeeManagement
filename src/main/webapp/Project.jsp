<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.util.List"%>
<%@ page import= "javax.servlet.http.HttpServletRequest" %>
<%@ page import= "javax.servlet.http.HttpServletResponse" %>
<%@ page import= "java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project</title>
<style>
    .heading {
        border: 5px outset blue;
        background-color: white;
        text-align: center;
    }
    .tableHead {
        text-align: center;
        background-color: cyan;
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
    List<List<String[]>> allProjects = (List<List<String[]>>) request.getAttribute("allProjectsList");
    int listSize = 0;
    if (null != allProjects) {
        listSize = allProjects.size();
    }
%>
<body bgcolor="cyan">
    <div class="heading">
        <h3>PROJECT VIEW</h3>
    </div>
    <%
            if(null != errorMessage) {
        	    %>
        	    <div class="alertMessage">
        	        <p><%= errorMessage %></p>
        	    </div>
         <% }%>
         
         <div class="menuSpace">
             <form action="project?action=createProject" method="post">
                 <input type="submit" value="Create project">
             </form>
        </div>
    
        <div class="menuSpace">
            <form action="project" method="get">
                <input type="hidden" name="action" value="getDeletedProjects">
                <input type="submit" value="View inactive projects">
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
                <h4>LIST OF ACTIVE PROJECTS</h4>
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
                <% for (List<String[]> project : allProjects) {
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
                           <td align="left">
                              <div class="buttonSpace">
                               <form action="project" method="get">
                                   <input type="hidden" name="action" value="singleProject">
                                   <input type="hidden" name="proj_id" value="<%= basicInfo[0] %>">
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
           <form action="project" method="get">
               <h4>SEARCH A PROJECT</h4> <br><br>
               <input type="hidden" name="action" value="singleProject">
               Project ID : <input type="text" name="proj_id"><br><br>
               <input type="submit" value="Search">
           </form>
        </div> 
  <% } else {
            %> 
                <div class="tableHead">
                     <h3> NO PROJECTS AVAILABLE NOW</h3>
                </div>
            <%
      }
    
    %>
</body>
</html>