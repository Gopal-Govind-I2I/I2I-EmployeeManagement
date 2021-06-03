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
<title>Assign Projects</title>
<style>
.heading {
   border: 5px outset blue;
   background-color: white;
   text-align: center;
}
    
.alertMessage {
   text-align: center;
   background-color: red;
}

.subHead {
   background-color: cyan;
   text-align: center;
   margin: 20px;
   padding: 20px;
}
    
.formSpace {
   background-color: cyan;
   text-align: center;
   margin: 30px;
   padding: 30px;
} 
    
.boxSpace {
   background-color: cyan;
   text-align: center;
   margin: 5px;
   padding: 5px;
}
</style>
</head>
<%
    List<String[]> assignableProjects = (List<String[]>)request.getAttribute("assignableProjects");
    String employeeID = (String)request.getAttribute("employeeID");
    int listSize = 0;
    if (null != assignableProjects) {
    	listSize = assignableProjects.size();
    }
%>
<body bgcolor="cyan">
    <div class="heading">
        <h3>PROJECT ASSIGNMENT</h3>
    </div>
    
    <div class="subHead">
        <a href="index.jsp"> 
            <input type="button" value="Main menu" />
        </a>
    </div>
               <div class="subHead">
                   <h4>Projects available for assignment</h4>
               </div>
        <% if (0 < listSize) { %>
             <div class="formSpace">
                <form action="employee?action=assignProjects" method="post">
                <input type="hidden" name="emp_id" value="<%= employeeID %>">
           <%      for (String[] project : assignableProjects) {  %>
                    <div class="boxSpace">
                        <input type="checkbox" name="projects" value="<%= project[0] %>"/><%= project[1] %>
                    </div>
           <%    } %>
                     <br><br>
                     <input type="submit" value="Assign"/>
                 </form>
             </div>
     <%     } else { %>
               <div class="subHead">
                   <h4>No assignable project as of now.</h4>
               </div>
      <% } %>
</body>
</html>