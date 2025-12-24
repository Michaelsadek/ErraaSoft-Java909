<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Details</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #eef2f3; padding: 50px; }
        .container { background-color: #fff; padding: 20px; border-radius: 10px; width: 400px; margin: auto; box-shadow: 0px 0px 15px rgba(0,0,0,0.3); }
        h2 { text-align: center; color: #333; }
        p { font-size: 16px; color: #555; margin: 10px 0; }
    </style>
</head>
<body>
    <div class="container">
        <h2>User Details</h2>
        <p><strong>Full Name:</strong> ${fullName}</p>
        <p><strong>Password:</strong> ${password}</p>
        <p><strong>Age:</strong> ${age}</p>
        <p><strong>Address (Radio):</strong> ${addressRadio}</p>
        <p><strong>Address (Select):</strong> ${addressSelect}</p>
    </div>
</body>
</html>
