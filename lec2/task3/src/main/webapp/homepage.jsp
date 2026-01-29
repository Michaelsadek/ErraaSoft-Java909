<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Homepage</title>
</head>
<body>

<h2>Homepage</h2>

<%
    String favPlace = request.getParameter("favPlace");

    // If user submitted a new favorite place
    if (favPlace != null && !favPlace.trim().isEmpty()) {

        Cookie cookie = new Cookie("favPlace", favPlace);

        // 1 month = 30 days
        cookie.setMaxAge(60 * 60 * 24 * 30);

        response.addCookie(cookie);
    }

    // Read cookies
    String savedPlace = null;
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
        for (Cookie c : cookies) {
            if ("favPlace".equals(c.getName())) {
                savedPlace = c.getValue();
                break;
            }
        }
    }
%>

<%
    if (savedPlace != null) {
%>
        <p>Your favorite place is: <strong><%= savedPlace %></strong></p>
<%
    } else {
%>
        <p>No favorite place saved yet.</p>
<%
    }
%>

<br>
<a href="start.html">Change Favorite Place</a>

</body>
</html>
