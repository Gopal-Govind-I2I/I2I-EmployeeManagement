<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.io.PrintWriter" %>
<%@ page import= "java.util.List"%>

<%@ page import= "javax.servlet.http.HttpServletRequest" %>
<%@ page import= "javax.servlet.http.HttpServletResponse" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Assign Employees</title>
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
    List<String[]> assignableEmployees = (List<String[]>)request.getAttribute("assignableEmployees");
    int projectID = (Integer)request.getAttribute("projectID");
    int listSize = 0;
    if (null != assignableEmployees) {
        listSize = assignableEmployees.size();
    }
%>
<body bgcolor="cyan">
    <div class="heading">
        <h3>EMPLOYEE ASSIGNMENT</h3>
    </div>
    
    <div class="subHead">
        <a href="index.jsp"> 
            <input type="button" value="Main menu" />
        </a>
    </div>
                  <div class="subHead">
                      <h4>Employees available for assignment</h4>
                  </div>
        <% if (0 < listSize) { %>
             <div class="formSpace">
                <form action="project?action=assignEmployees" method="post">
                <input type="hidden" name="proj_id" value="<%= projectID %>">
           <%      for (String[] employee : assignableEmployees) {  %>
                    <div class="boxSpace">
                        <input type="checkbox" name="employees" value="<%= employee[0] %>"/><%= employee[1] %>
                    </div>
           <%    } %>
                     <br><br>
                     <input type="submit" value="Assign"/>
                 </form>
             </div>
     <%     } else { %>
               <div class="subHead">
                   <h4>No assignable employee as of now.</h4>
               </div>
      <% } %>
</body>
</html>