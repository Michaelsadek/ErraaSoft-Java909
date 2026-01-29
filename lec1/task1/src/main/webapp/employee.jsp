<%@page import="model.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee Info</title>
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f4f6f8;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    .container {
        background-color: #fff;
        padding: 30px 50px;
        border-radius: 10px;
        box-shadow: 0 8px 20px rgba(0,0,0,0.1);
        text-align: center;
    }

    h2 {
        color: #333;
        margin-bottom: 20px;
    }

    p {
        font-size: 18px;
        color: #555;
        background-color: #eef2f7;
        padding: 15px;
        border-radius: 5px;
        display: inline-block;
    }
</style>
</head>
<body>

<%
    Employee emp = new Employee(202, "John Doe");
    String employeeInfo = emp.getEmployeeInfo();
%>

<div class="container">
    <h2>Employee Information</h2>
    <p><%= employeeInfo %></p>
</div>

</body>
</html>
