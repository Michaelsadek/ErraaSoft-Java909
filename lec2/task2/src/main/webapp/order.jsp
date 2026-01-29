<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
    <title>Order Food</title>
</head>
<body>

<h2>Order Food</h2>

<form method="post">
    <label>Food Name:</label><br>
    <input type="text" name="food" required>
    <br><br>
    <input type="submit" value="Add Order">
</form>

<%
    String food = request.getParameter("food");

    if (food != null && !food.trim().isEmpty()) {

        // Get orders list from session
        List<String> orders =
            (List<String>) session.getAttribute("orders");

        // If first order, create list
        if (orders == null) {
            orders = new ArrayList<>();
        }

        // Add new food
        orders.add(food);

        // Save back to session
        session.setAttribute("orders", orders);
    }
%>

<br>
<a href="allorders.jsp">View All Orders</a>

</body>
</html>
