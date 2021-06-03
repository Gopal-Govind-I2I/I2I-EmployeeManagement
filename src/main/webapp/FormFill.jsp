<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Employee create/edit</title>
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
    String name = "";String id = "";String dateOfBirth = "";String email = "";double salary = 0.0;
    String doorNo = "";boolean isPermanent = true;String street = ""; String locality = ""; String pincode = "";
    String district = "";String state = "";boolean found = false;String actionField = "addNewEmployee";
    boolean createFlag = true; boolean updateFlag = false;String title = "Create Employee Form";boolean insertAddressFlag = false;
    boolean basicsFlag = false;boolean addressFlag = false;String appendString = "Mandatory Permanent";
    String[] basicInfo;String address[];String addressId = "";int addrId = 0;int addressID=0;
    List<String[]> employee = (List<String[]>) request.getAttribute("employeeDetails");
    if (null != employee) {
    	createFlag = false;
    	updateFlag = true;
    	appendString = "";
    	title = "Update Employee Form";
    	String updateAction = (String) request.getAttribute("updateAction");
    	if ("basicDetails".equals(updateAction)) {
    		actionField = "updateEmployeeDetails";
    		basicInfo = employee.get(0);
    	    id = basicInfo[0];name = basicInfo[1];dateOfBirth = basicInfo[2];
    	    email = basicInfo[3];salary = Double.parseDouble(basicInfo[4]);
    	    basicsFlag = true;
    	} else if ("address".equals(updateAction)) {
    		basicInfo = employee.get(0);id = basicInfo[0];
    		actionField = "updateEmployeeAddress";addressFlag = true;
            String addressMeta[] = employee.get(1);int addrSize = Integer.parseInt(addressMeta[0]);
            addressID = Integer.parseInt((String)request.getParameter("addressId"));int base = 2;
            for (int idx = 0; (idx < addrSize && !found); idx++) {
            	address = employee.get(base + idx);
            	int aid = Integer.parseInt(address[0]);
            	if (aid == addressID) {
            		found= true;isPermanent = false;doorNo = address[2];street = address[3];
            		locality = address[4];pincode = address[5];district=address[6];state=address[7];
            	}
            }
    	} else if("addNewAddress".equals(updateAction)) {
    		basicInfo = employee.get(0);id = basicInfo[0];isPermanent = false;
    		actionField = "insertAddress";insertAddressFlag = true;
    	}
    }
    if (!createFlag && null == employee) {
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
        <form action="employee?action=<%= actionField  %>" method="post">
            <% if (createFlag || basicsFlag) { %>
                <h3>Basic Employee Details</h3>
                Employee ID : <input type="text" name="employeeId" 
                             <% if(basicsFlag) {
                                 %> value="<%= id %>" readonly <%
                             } %>><br><br>
                Name : <input type="text" name="name" 
                             <% if(basicsFlag) {
                                 %> value="<%= name %>" <%
                             } %>><br><br>
                Date of birth: <input type="date" name="dateOfBirth" 
                             <% if(basicsFlag) {
                                 %> value="<%= dateOfBirth %>" <%
                             } %>><br><br>
                Email: <input type="text" name="email" pattern="^[a-zA-Z0-9._]+@[a-zA-Z0-9.]+$"
                             <% if(basicsFlag) {
                                 %> value="<%= email %>" <%
                             } %>><br><br>
                Salary: <input type="text" name="salary" 
                             <% if(basicsFlag) {
                                 %> value="<%= salary %>" <%
                             } %>><br><br>
            <% } %>
             
            <% if (createFlag || (addressFlag || insertAddressFlag)) { %>
                <h3><%= appendString %> Address Details</h3>
                <% if(addressFlag) {
                    %>
                       <input type="hidden" name="addressId" value=<%= addressID %>><br><br>
                 <% } %>
                 <% if(addressFlag || insertAddressFlag) {
                    %>
                       <input type="hidden" name="employeeId" value="<%= id %>">
                 <% } %>
                Permanent Address : <input type="text" name="isPermanent" 
                                     value="<%= isPermanent %>" readonly><br><br>
                Door no: <input type="text" name="doorNo" 
                             <% if(addressFlag) {
                                 %> value="<%= doorNo %>" <%
                             } %>><br><br>
                Street: <input type="text" name="street" 
                             <% if(addressFlag) {
                                 %> value="<%= street %>" <%
                             } %>><br><br>
                Locality: <input type="text" name="locality" 
                             <% if(addressFlag) {
                                 %> value="<%= locality %>" <%
                             } %>><br><br>
                Pincode: <input type="number" name="pincode" 
                          min="100000" max="999999"
                             <% if(addressFlag) {
                                 %> value="<%= pincode %>" <%
                             } %>><br><br>
                District: <input type="text" name="district" 
                             <% if(addressFlag) {
                                 %> value="<%= district %>" <%
                             } %>><br><br>
                State: <input type="text" name="state" 
                             <% if(addressFlag) {
                                 %> value="<%= state %>" <%
                             } %>><br><br>
             <% } %>
            
             <input type="submit" <% if(basicsFlag || addressFlag) {
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