package task2;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form parameters
        String fullName = request.getParameter("fullName");
        String password = request.getParameter("password");
        String age = request.getParameter("age");
        String addressRadio = request.getParameter("addressRadio");
        String addressSelect = request.getParameter("addressSelect");

        // Set attributes to pass to JSP
        request.setAttribute("fullName", fullName);
        request.setAttribute("password", password);
        request.setAttribute("age", age);
        request.setAttribute("addressRadio", addressRadio);
        request.setAttribute("addressSelect", addressSelect);

        // Forward to display.jsp
        request.getRequestDispatcher("display.jsp").forward(request, response);
    }
}