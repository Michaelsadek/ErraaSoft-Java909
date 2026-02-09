<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>ADD Item</title>
  <style>
    * { margin: 0; padding: 0; outline: none; box-sizing: border-box; font-family: 'Poppins', sans-serif; }
    body { display: flex; align-items: center; justify-content: center; min-height: 100vh; padding: 40px; 
           background: linear-gradient(135deg, #71b7e6, #9b59b6); }
    .container { max-width: 700px; width: 100%; background: rgba(255, 255, 255, 0.95); padding: 40px 50px; 
                 border-radius: 20px; box-shadow: 0 15px 50px rgba(0, 0, 0, 0.2); backdrop-filter: blur(10px); }
    .text { font-size: 3rem; font-weight: 700; text-align: center; 
            background: linear-gradient(45deg, #71b7e6, #9b59b6); -webkit-background-clip: text; 
            -webkit-text-fill-color: transparent; margin-bottom: 50px; }
    .form-row { display: flex; margin-bottom: 40px; flex-wrap: wrap; gap: 30px; }
    .input-data { width: 100%; height: 70px; position: relative; flex: 1; min-width: 250px; }
    .input-data input { display: block; width: 100%; height: 100%; border: none; font-size: 1.1rem; 
                        background: transparent; padding-top: 15px; color: #333; 
                        border-bottom: 2px solid rgba(0, 0, 0, 0.12); transition: all 0.3s ease; }
    .input-data input:focus { border-bottom-color: #3498db; }
    .input-data label { position: absolute; bottom: 25px; left: 0; color: #666; font-size: 1.1rem; 
                        pointer-events: none; transition: all 0.3s ease; font-weight: 500; }
    .input-data input:focus ~ label, .input-data input:valid ~ label { transform: translateY(-35px); 
                                                                        font-size: 0.95rem; color: #3498db; }
    .button { display: block; width: 100%; max-width: 300px; margin: 60px auto 40px; padding: 18px 30px; 
              font-size: 1.2rem; font-weight: 600; letter-spacing: 1px; text-transform: uppercase; 
              border: none; border-radius: 50px; cursor: pointer; background: linear-gradient(45deg, #71b7e6, #9b59b6); 
              color: white; transition: all 0.4s ease; box-shadow: 0 10px 30px rgba(113, 183, 230, 0.4); }
    .button:hover { transform: translateY(-3px); box-shadow: 0 15px 40px rgba(113, 183, 230, 0.6); }
    .back { text-align: center; margin-top: 30px; }
    .back a { color: #666; text-decoration: none; font-size: 1.1rem; font-weight: 500; }
    .back a:hover { color: #3498db; }
    .error { color: #e74c3c; text-align: center; margin-bottom: 20px; font-size: 0.9rem; }
  </style>
</head>
<body>
<div class="container">
  <div class="text">Add Item</div>
  
  <% 
    String errorMsg = (String) session.getAttribute("errorMessage");
    if (errorMsg != null) { 
  %>
    <div class="error"><%= errorMsg %></div>
  <% 
      session.removeAttribute("errorMessage");
    } 
  %>
  
  <form action="<%=request.getContextPath()%>/ItemController" method="POST" onsubmit="return validateForm()">
    <div class="form-row">
      <div class="input-data">
        <input type="text" name="name" id="name" required 
               pattern="[A-Za-z0-9\s]{3,50}" 
               title="Name must be 3-50 alphanumeric characters" 
               maxlength="50">
        <label>Name</label>
      </div>
      <div class="input-data">
        <input type="number" name="price" id="price" required 
               min="0.01" step="0.01" 
               title="Price must be a positive number">
        <label>PRICE</label>
      </div>
    </div>
    <div class="form-row">
      <div class="input-data">
        <input type="number" name="totalNumber" id="totalNumber" required 
               min="0" step="1" 
               title="Total number must be a non-negative integer">
        <label>TOTAL_NUMBER</label>
      </div>
    </div>
    
    <input type="hidden" name="action" value="add-item">
    <input type="submit" value="Add" class="button">
  </form>

  <p class="back">
    <a href="<%=request.getContextPath()%>/ItemController?action=show-items">Back To Items</a>
  </p>
</div>

<script>
function validateForm() {
    const name = document.getElementById('name').value.trim();
    const price = parseFloat(document.getElementById('price').value);
    const totalNumber = parseInt(document.getElementById('totalNumber').value);
    
    if (name.length < 3) {
        alert("Item name must be at least 3 characters long");
        return false;
    }
    
    if (isNaN(price) || price <= 0) {
        alert("Price must be greater than 0");
        return false;
    }
    
    if (isNaN(totalNumber) || totalNumber < 0) {
        alert("Total number cannot be negative");
        return false;
    }
    
    return confirm("Are you sure you want to add this item?");
}
</script>
</body>
</html>