<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <style>
    * { margin: 0; padding: 0; outline: none; box-sizing: border-box; font-family: 'Poppins', sans-serif; }
    body { display: flex; align-items: center; justify-content: center; min-height: 100vh; padding: 40px; 
           background: linear-gradient(135deg, #667eea, #764ba2); }
    .container { max-width: 500px; width: 100%; background: rgba(255, 255, 255, 0.95); padding: 40px 50px; 
                 border-radius: 20px; box-shadow: 0 15px 50px rgba(0, 0, 0, 0.2); backdrop-filter: blur(10px); }
    .text { font-size: 3rem; font-weight: 700; text-align: center; 
            background: linear-gradient(45deg, #667eea, #764ba2); -webkit-background-clip: text; 
            -webkit-text-fill-color: transparent; margin-bottom: 50px; }
    .form-row { display: flex; margin-bottom: 40px; flex-wrap: wrap; gap: 30px; }
    .input-data { width: 100%; height: 70px; position: relative; flex: 1; min-width: 250px; }
    .input-data input { display: block; width: 100%; height: 100%; border: none; font-size: 1.1rem; 
                        background: transparent; padding-top: 15px; color: #333; 
                        border-bottom: 2px solid rgba(0, 0, 0, 0.12); transition: all 0.3s ease; }
    .input-data input:focus { border-bottom-color: #667eea; }
    .input-data label { position: absolute; bottom: 25px; left: 0; color: #666; font-size: 1.1rem; 
                        pointer-events: none; transition: all 0.3s ease; font-weight: 500; }
    .input-data input:focus ~ label, .input-data input:valid ~ label { transform: translateY(-35px); 
                                                                        font-size: 0.95rem; color: #667eea; }
    .button { display: block; width: 100%; max-width: 300px; margin: 60px auto 40px; padding: 18px 30px; 
              font-size: 1.2rem; font-weight: 600; letter-spacing: 1px; text-transform: uppercase; 
              border: none; border-radius: 50px; cursor: pointer; background: linear-gradient(45deg, #667eea, #764ba2); 
              color: white; transition: all 0.4s ease; box-shadow: 0 10px 30px rgba(102, 126, 234, 0.4); }
    .button:hover { transform: translateY(-3px); box-shadow: 0 15px 40px rgba(102, 126, 234, 0.6); }
    .back { text-align: center; margin-top: 30px; }
    .back a { color: #666; text-decoration: none; font-size: 1.1rem; font-weight: 500; }
    .back a:hover { color: #667eea; }
    .error { color: #e74c3c; text-align: center; margin-bottom: 20px; font-size: 0.9rem; }
    .checkbox-group { display: flex; align-items: center; margin-top: 20px; padding-left: 10px; }
    .checkbox-group input { width: 20px; height: 20px; margin-right: 10px; }
    .checkbox-group label { position: static; transform: none !important; font-size: 1rem !important; }
  </style>
</head>
<body>
<div class="container">
  <div class="text">Login</div>
  
  <% 
    String errorMsg = (String) request.getAttribute("errorMessage");
    if (errorMsg != null) { 
  %>
    <div class="error"><%= errorMsg %></div>
  <% } %>
  
  <form action="<%=request.getContextPath()%>/login" method="POST">
    <div class="form-row">
      <div class="input-data">
        <input type="email" name="email" id="email" required 
               value="${cookie.userEmail.value != null ? cookie.userEmail.value : ''}">
        <label>Email</label>
      </div>
    </div>
    <div class="form-row">
      <div class="input-data">
        <input type="password" name="password" id="password" required>
        <label>Password</label>
      </div>
    </div>
    
    <div class="checkbox-group">
      <input type="checkbox" name="rememberMe" id="rememberMe">
      <label for="rememberMe">Remember me</label>
    </div>
    
    <input type="submit" value="Login" class="button">
  </form>

  <p class="back">
    <a href="<%=request.getContextPath()%>/signup">Create an account</a>
  </p>
</div>
</body>
</html>