<%@ page import="model.Item" %>
<%@ page import="model.ItemDetails" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Show Items</title>
  <style>
    * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Poppins', sans-serif; }
    body { display: flex; align-items: center; justify-content: center; min-height: 100vh; padding: 40px; 
           background: linear-gradient(135deg, #667eea, #764ba2); }
    .layer { background: rgba(255, 255, 255, 0.95); border-radius: 20px; 
             box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3); padding: 40px; 
             max-width: 1400px; width: 100%; backdrop-filter: blur(10px); }
    .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
    h1 { text-align: center; color: #333; font-size: 2.8rem; font-weight: 700; 
         background: linear-gradient(45deg, #667eea, #764ba2); -webkit-background-clip: text; 
         -webkit-text-fill-color: transparent; flex: 1; }
    .user-info { display: flex; align-items: center; gap: 20px; color: #333; }
    .logout-btn { background: #e74c3c; color: white; padding: 10px 20px; border-radius: 25px; 
                  text-decoration: none; font-weight: 600; }
    table { width: 100%; border-collapse: separate; border-spacing: 0; margin-bottom: 40px; 
            overflow: hidden; border-radius: 15px; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1); }
    thead { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); }
    th { color: white; font-weight: 600; text-transform: uppercase; letter-spacing: 1px; 
         font-size: 0.95rem; padding: 20px 15px; text-align: left; }
    td { padding: 18px 15px; color: #555; font-size: 0.95rem; border-bottom: 1px solid rgba(0, 0, 0, 0.05); }
    tr:hover { background-color: rgba(102, 126, 234, 0.1); }
    .details-box { background: #f0f4ff; padding: 10px; border-radius: 8px; font-size: 0.85rem; }
    .details-box strong { color: #667eea; }
    .expired { color: #e74c3c; font-weight: 600; }
    .no-details { color: #999; font-style: italic; }
    td a { display: inline-block; padding: 8px 18px; margin-right: 8px; margin-bottom: 5px; 
           border-radius: 25px; text-decoration: none; font-weight: 500; font-size: 0.85rem; 
           transition: all 0.3s ease; }
    .btn-update { background: linear-gradient(45deg, #4CAF50, #8BC34A); color: white; }
    .btn-delete { background: linear-gradient(45deg, #f44336, #FF9800); color: white; }
    .btn-details { background: linear-gradient(45deg, #3498db, #2980b9); color: white; }
    .btn-delete-details { background: linear-gradient(45deg, #e67e22, #d35400); color: white; }
    .hidden { display: none !important; }
    .f { display: block; margin: 0 auto; padding: 0; border: none; background: none; cursor: pointer; }
    .f a { display: inline-block; padding: 18px 40px; background: linear-gradient(45deg, #667eea, #764ba2); 
           color: white; text-decoration: none; border-radius: 50px; font-weight: 600; font-size: 1.1rem; }
    .success-msg { background: #d4edda; color: #155724; padding: 15px; border-radius: 10px; 
                   margin-bottom: 20px; text-align: center; }
    .error-msg { background: #f8d7da; color: #721c24; padding: 15px; border-radius: 10px; 
                 margin-bottom: 20px; text-align: center; }
  </style>
</head>
<body>
<div class="layer">
  <div class="header">
    <h1>Items</h1>
    <div class="user-info">
      <span>Welcome, ${sessionScope.currentUser.name}</span>
      <a href="<%=request.getContextPath()%>/logout" class="logout-btn">Logout</a>
    </div>
  </div>
  
  <% 
    String successMsg = (String) request.getAttribute("successMessage");
    String errorMsg = (String) request.getAttribute("errorMessage");
    if (successMsg != null) { 
  %>
    <div class="success-msg"><%= successMsg %></div>
  <% } else if (errorMsg != null) { %>
    <div class="error-msg"><%= errorMsg %></div>
  <% } %>
  
  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>NAME</th>
        <th>PRICE</th>
        <th>TOTAL_NUMBER</th>
        <th>DETAILS</th>
        <th>ACTION</th>
      </tr>
    </thead>
    <tbody>
    <%
      List<Item> items = (List<Item>) request.getAttribute("allItems");
      if (items != null) {
        for(Item item : items) {
          boolean hasDetails = item.hasDetails();
          ItemDetails details = item.getDetails();
    %>
      <tr>
        <td><%= item.getId() %></td>
        <td><strong><%= item.getName() %></strong></td>
        <td>$<%= String.format("%.2f", item.getPrice()) %></td>
        <td><%= item.getTotalNumber() %></td>
        <td>
          <% if (hasDetails && details != null) { %>
            <div class="details-box">
              <div><strong>Desc:</strong> <%= details.getDescription() %></div>
              <div><strong>Issued:</strong> <%= details.getIssueDate() %></div>
              <div><strong>Expires:</strong> <%= details.getExpiryDate() %>
                <% if (details.isExpired()) { %>
                  <span class="expired">(EXPIRED)</span>
                <% } %>
              </div>
            </div>
          <% } else { %>
            <span class="no-details">No details</span>
          <% } %>
        </td>
        <td>
          <a href="<%=request.getContextPath()%>/ItemController?action=show-item&id=<%= item.getId() %>" 
             class="btn-update">Update</a>
          <a href="<%=request.getContextPath()%>/ItemController?action=remove-item&id=<%= item.getId() %>" 
             class="btn-delete" onclick="return confirm('Delete this item?')">Delete</a>
          
          <!-- Add Details Button - Hidden if has details -->
          <a href="<%=request.getContextPath()%>/ItemController?action=show-details&id=<%= item.getId() %>" 
             class="btn-details <%= hasDetails ? "hidden" : "" %>">Add Details</a>
          
          <!-- Delete Details Button - Hidden if no details -->
          <a href="<%=request.getContextPath()%>/ItemController?action=delete-details&id=<%= item.getId() %>" 
             class="btn-delete-details <%= hasDetails ? "" : "hidden" %>"
             onclick="return confirm('Delete details only?')">Delete Details</a>
        </td>
      </tr>
    <% 
        }
      }
    %>
    </tbody>
  </table>

  <button class="f"><a href="<%=request.getContextPath()%>/item/add-item.jsp">Add Item</a></button>
</div>
</body>
</html>