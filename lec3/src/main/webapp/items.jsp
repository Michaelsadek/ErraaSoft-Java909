<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "model.Item" %>
<%@ page import="java.util.List" %>
<%@ page import = "service.ItemUtilService" %>

<!DOCTYPE html>
<html>
<head>
    <title>Items Page</title>
</head>
<body>

<h2>Items Service Results</h2>

<%
    // Create service
    ItemUtilService service = new ItemUtilService();

    // Create sample items
    List<Item> items = List.of(
        new Item(1, "Milk", 30),
        new Item(2, "Rice", 60),
        new Item(3, "Fish", 40),
        new Item(4, "Apple", 10)
    );

    // Save items
    service.saveItems(items);

    // Call service methods
    List<Item> idFiltered = service.getItemsByIdsOneAndTwo();
    List<Item> nameFiltered = service.getItemsNameContainsI();
    List<Item> priceFiltered = service.getItemsPriceGreaterThan50AndLessThan20();
%>

<hr>

<h3>Items with ID = 1 or 2</h3>
<ul>
    <% for (Item item : idFiltered) { %>
        <li><%= item.getName() %> - <%= item.getPrice() %></li>
    <% } %>
</ul>

<hr>

<h3>Items where name contains 'i'</h3>
<ul>
    <% for (Item item : nameFiltered) { %>
        <li><%= item.getName() %> - <%= item.getPrice() %></li>
    <% } %>
</ul>

<hr>

<h3>Items where price > 50 AND price < 20</h3>
<p><strong>This list will always be empty due to logical condition</strong></p>

<ul>
    <% for (Item item : priceFiltered) { %>
        <li><%= item.getName() %> - <%= item.getPrice() %></li>
    <% } %>
</ul>

</body>
</html>
