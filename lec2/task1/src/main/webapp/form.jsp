<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Form</title>
</head>
<body>

<h2>User Information</h2>

<form action="user" method="post">
    <label>Full Name:</label><br>
    <input type="text" name="fullName" required><br><br>

    <label>Age:</label><br>
    <input type="number" name="age" required><br><br>

    <input type="submit" value="Submit">
</form>

</body>
</html>
