<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <title>All Orders</title>
</head>
<body>

<h2>All Orders in This Session</h2>

<%
    List<String> orders =
        (List<String>) session.getAttribute("orders");

    if (orders == null || orders.isEmpty()) {
%>
        <p>No orders yet.</p>
<%
    } else {
%>
        <ul>
            <% for (String order : orders) { %>
                <li><%= order %></li>
            <% } %>
        </ul>
<%
    }
%>

<br>
<a href="order.jsp">Add More Orders</a>

</body>
</html>
