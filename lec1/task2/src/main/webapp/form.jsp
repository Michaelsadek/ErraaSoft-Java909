<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Form</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 50px; }
        .container { background-color: #fff; padding: 20px; border-radius: 10px; width: 400px; margin: auto; box-shadow: 0px 0px 10px rgba(0,0,0,0.2); }
        h2 { text-align: center; color: #333; }
        label { display: block; margin-top: 10px; }
        input[type=text], input[type=password], select { width: 100%; padding: 8px; margin-top: 5px; border-radius: 5px; border: 1px solid #ccc; }
        input[type=submit] { width: 100%; padding: 10px; margin-top: 15px; background-color: #4CAF50; color: white; border: none; border-radius: 5px; cursor: pointer; }
        input[type=submit]:hover { background-color: #45a049; }
    </style>
</head>
<body>
    <div class="container">
        <h2>User Form</h2>
        <form action="UserServlet" method="post">
            <label>Full Name:</label>
            <input type="text" name="fullName" required>

            <label>Password:</label>
            <input type="password" name="password" required>

            <label>Age:</label>
            <input type="text" name="age" required>

            <label>Address (Radio):</label>
            <input type="radio" name="addressRadio" value="Cairo" /> Cairo
            <input type="radio" name="addressRadio" value="Alex" /> Alex
            <input type="radio" name="addressRadio" value="Menofia" /> Menofia

            <label>Address (Select):</label>
            <select name="addressSelect">
                <option value="Cairo">Cairo</option>
                <option value="Alex">Alex</option>
                <option value="Menofia">Menofia</option>
            </select>

            <input type="submit" value="Submit">
        </form>
    </div>
</body>
</html>
