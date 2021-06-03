<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style>
.heading {
  border: 5px outset blue;
  background-color: white;
  text-align: center;
}
.options {
  background-color: cyan;
  text-align: center;
  margin: 40px;
  padding: 30px;
}
.alertMessage {
        text-align: center;
        background-color: red;
}
</style>
<title>Index</title>
</head>
<%
    String errorMessage = (String)request.getAttribute("errorMsg");
    PrintWriter printWriter = response.getWriter();
    //List<List<String[]>> allEmployees = null;
%>
<body bgcolor="cyan">
    <div class="heading">
        <h3>EMPLOYEE PROJECT MANAGEMENT</h3>
    </div>
    <%
            if(null != errorMessage) {
        	    %>
        	    <div class="alertMessage">
        	        <p><%= errorMessage %></p>
        	    </div>
    <% }%>
    <div class="options">
        <form action="employee" method="get">
            <input type="hidden" name="action" value="displayAll">
            <input type="submit" value="Employee Perspective">
        </form>
    </div>
    <div class="options">
        <form action="project" method="get">
            <input type="hidden" name="action" value="displayAll">
            <input type="submit" value="Project Perspective">
        </form>
    </div>
</body>
</html>