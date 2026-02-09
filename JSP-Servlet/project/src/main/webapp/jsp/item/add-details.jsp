<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Add Item Details</title>
  <style>
    * { margin: 0; padding: 0; outline: none; box-sizing: border-box; font-family: 'Poppins', sans-serif; }
    body { display: flex; align-items: center; justify-content: center; min-height: 100vh; padding: 40px; 
           background: linear-gradient(135deg, #3498db, #2980b9); }
    .container { max-width: 700px; width: 100%; background: rgba(255, 255, 255, 0.95); padding: 40px 50px; 
                 border-radius: 20px; box-shadow: 0 15px 50px rgba(0, 0, 0, 0.2); backdrop-filter: blur(10px); }
    .text { font-size: 3rem; font-weight: 700; text-align: center; 
            background: linear-gradient(45deg, #3498db, #2980b9); -webkit-background-clip: text; 
            -webkit-text-fill-color: transparent; margin-bottom: 50px; }
    .form-row { display: flex; margin-bottom: 40px; flex-wrap: wrap; gap: 30px; }
    .input-data { width: 100%; height: 70px; position: relative; flex: 1; min-width: 250px; }
    .input-data input, .input-data textarea { display: block; width: 100%; height: 100%; border: none; font-size: 1.1rem; 
                        background: transparent; padding-top: 15px; color: #333; 
                        border-bottom: 2px solid rgba(0, 0, 0, 0.12); transition: all 0.3s ease; }
    .input-data textarea { height: 100px; resize: vertical; padding-top: 25px; }
    .input-data input:focus, .input-data textarea:focus { border-bottom-color: #3498db; }
    .input-data label { position: absolute; bottom: 25px; left: 0; color: #666; font-size: 1.1rem; 
                        pointer-events: none; transition: all 0.3s ease; font-weight: 500; }
    .input-data textarea ~ label { bottom: 75px; }
    .input-data input:focus ~ label, .input-data input:valid ~ label,
    .input-data textarea:focus ~ label, .input-data textarea:valid ~ label { transform: translateY(-35px); 
                                                                        font-size: 0.95rem; color: #3498db; }
    .button { display: block; width: 100%; max-width: 300px; margin: 60px auto 40px; padding: 18px 30px; 
              font-size: 1.2rem; font-weight: 600; letter-spacing: 1px; text-transform: uppercase; 
              border: none; border-radius: 50px; cursor: pointer; background: linear-gradient(45deg, #3498db, #2980b9); 
              color: white; transition: all 0.4s ease; box-shadow: 0 10px 30px rgba(52, 152, 219, 0.4); }
    .button:hover { transform: translateY(-3px); box-shadow: 0 15px 40px rgba(52, 152, 219, 0.6); }
    .back { text-align: center; margin-top: 30px; }
    .back a { color: #666; text-decoration: none; font-size: 1.1rem; font-weight: 500; }
    .back a:hover { color: #3498db; }
  </style>
</head>
<body>
<div class="container">
  <div class="text">Add Details</div>
  
  <form action="<%=request.getContextPath()%>/ItemController" method="POST" onsubmit="return validateForm()">
    <input type="hidden" name="action" value="add-details">
    <input type="hidden" name="itemId" value="${param.id}">
    
    <div class="form-row">
      <div class="input-data" style="height: 100px;">
        <textarea name="description" id="description" required></textarea>
        <label>Description</label>
      </div>
    </div>
    <div class="form-row">
      <div class="input-data">
        <input type="date" name="issueDate" id="issueDate" required>
        <label>Issue Date</label>
      </div>
      <div class="input-data">
        <input type="date" name="expiryDate" id="expiryDate" required>
        <label>Expiry Date</label>
      </div>
    </div>
    
    <input type="submit" value="Add Details" class="button">
  </form>

  <p class="back">
    <a href="<%=request.getContextPath()%>/ItemController?action=show-items">Back To Items</a>
  </p>
</div>

<script>
document.getElementById('issueDate').valueAsDate = new Date();

function validateForm() {
    const issueDate = document.getElementById('issueDate').value;
    const expiryDate = document.getElementById('expiryDate').value;
    
    if (new Date(expiryDate) <= new Date(issueDate)) {
        alert("Expiry date must be after issue date");
        return false;
    }
    return true;
}
</script>
</body>
</html>