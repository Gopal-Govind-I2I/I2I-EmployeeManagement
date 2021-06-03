<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error Alert</title>
<style>
    .heading {
        border: 5px outset blue;
        background-color: white;
        text-align: center;
    }
    .alertMessage {
        text-align: center;
        background-color: red;
        margin: 30px;
        padding: 30px;
    }
    .menuSpace {
        background-color: cyan;
        text-align: center;
        margin: 20px;
        padding: 20px;
    }
</style>
</head>
<%
    String errorMessage = (String)request.getAttribute("errorMessage");
    PrintWriter printWriter = response.getWriter();
%>
<body bgcolor="cyan">
    <div class="heading">
        <h3>ERROR ALERT</h3>
    </div>
    <div class="alertMessage">
        <h4><%= errorMessage %></h4>
    </div>
    <div class="menuSpace">
        <a href="index.jsp"> 
            <input type="button" value="Main menu" />
        </a>
    </div>
</body>
</html>