<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project create/edit</title>
<style>
    .heading {
        border: 5px outset blue;
        background-color: white;
        text-align: center;
    }
    .menuSpace {
        background-color: cyan;
        text-align: center;
        margin: 20px;
        padding: 20px;
    }
    .formSpace {
        background-color: cyan;
        text-align: center;
        margin: 20px;
        padding: 40px;
    }
</style>
</head>
<% 
   int id = -1;String name = "";String manager = "";String client = "";String deadline = "";
   List<String[]> project = (List<String[]>) request.getAttribute("projectDetails");
   boolean createFlag = true;boolean updateFlag = false;String title = "Create Project";
   String actionField ="addNewProject";
   //String operation = (String)request.getAttribute("operation");
   if(null != project) {
	   updateFlag = true;createFlag = false;title = "Edit Project Details";
	   String basicInfo[] = project.get(0);
	   id = Integer.parseInt(basicInfo[0]);name = basicInfo[1];manager = basicInfo[2];
	   client = basicInfo[3]; deadline = basicInfo[4];actionField = "updateProject";
   }
   if (null == request.getAttribute("operation") && null == project) {
       request.setAttribute("errorMessage", "Form unavailable");
       RequestDispatcher rd = request.getRequestDispatcher("/ErrorDisplay.jsp");
       rd.forward(request, response);
   }
%>
<body bgcolor="cyan">
   <div class="heading">
        <h3><%= title %></h3>
    </div>
    <div class="formSpace">
           <form action="project?action=<%= actionField %>" method="post">
                <% if(updateFlag) {
                	%> <input type="hidden" name="proj_id" value=<%= id %>>
               <% } %>
                Name : <input type="text" name="name" 
                             <% if(updateFlag) {
                                 %> value="<%= name %>" <%
                             } %>><br><br>
                Manager: <input type="text" name="manager" 
                             <% if(updateFlag) {
                                 %> value="<%= manager %>" <%
                             } %>><br><br>
                Client: <input type="text" name="client" 
                             <% if(updateFlag) {
                                 %> value="<%= client %>" <%
                             } %>><br><br>
                Deadline: <input type="date" name="deadline" 
                             <% if(updateFlag) {
                                 %> value="<%= deadline %>" <%
                             } %>><br><br>
                <input type="submit" <% if(updateFlag) {
                                      %> value="Update" <%
                                  }  else {
                            	     %> value="Submit" <%
                                  }%> >
           </form>
    </div>
    <div class="menuSpace">
        <a href="index.jsp"> 
            <input type="button" value="Main menu" />
        </a>
    </div>
</body>
</html>