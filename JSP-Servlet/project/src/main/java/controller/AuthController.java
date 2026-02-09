package controller;

import model.User;
import service.UserService;
import service.impl.UserServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.Optional;

@WebServlet(urlPatterns = {"/login", "/signup", "/logout"})
public class AuthController extends HttpServlet {
	
	private UserService userService;
	
	@Override
	public void init() throws ServletException {
		this.userService = new UserServiceImpl();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String path = req.getServletPath();
		
		switch (path) {
			case "/logout":
				handleLogout(req, resp);
				break;
			case "/signup":
				req.getRequestDispatcher("/signup.jsp").forward(req, resp);
				break;
			default:
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String path = req.getServletPath();
		
		if ("/signup".equals(path)) {
			handleSignup(req, resp);
		} else {
			handleLogin(req, resp);
		}
	}
	
	private void handleLogin(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		Optional<User> userOpt = userService.authenticate(email, password);
		
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			HttpSession session = req.getSession();
			session.setAttribute("currentUser", user.toSafeUser());
			session.setAttribute("userId", user.getId());
			session.setMaxInactiveInterval(30 * 60);
			
			if ("on".equals(req.getParameter("rememberMe"))) {
				Cookie cookie = new Cookie("userEmail", user.getEmail());
				cookie.setMaxAge(7 * 24 * 60 * 60);
				resp.addCookie(cookie);
			}
			
			resp.sendRedirect(req.getContextPath() + "/ItemController");
		} else {
			req.setAttribute("errorMessage", "Invalid username or password");
			req.getRequestDispatcher("/login.jsp").forward(req, resp);
		}
	}
	
	private void handleSignup(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirmPassword");
		
		if (!password.equals(confirmPassword)) {
			req.setAttribute("errorMessage", "Passwords do not match");
			req.getRequestDispatcher("/signup.jsp").forward(req, resp);
			return;
		}
		
		if (password.length() < 6) {
			req.setAttribute("errorMessage", "Password must be at least 6 characters");
			req.getRequestDispatcher("/signup.jsp").forward(req, resp);
			return;
		}
		
		User newUser = new User(name, email, password);
		
		if (userService.createUser(newUser)) {
			userService.authenticate(email, password).ifPresent(user -> {
				HttpSession session = req.getSession();
				session.setAttribute("currentUser", user.toSafeUser());
				session.setAttribute("userId", user.getId());
			});
			resp.sendRedirect(req.getContextPath() + "/ItemController");
		} else {
			req.setAttribute("errorMessage", "Email already exists");
			req.setAttribute("name", name);
			req.getRequestDispatcher("/signup.jsp").forward(req, resp);
		}
	}
	
	private void handleLogout(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				cookie.setValue(null);
				cookie.setPath("/");
				resp.addCookie(cookie);
			}
		}
		
		resp.sendRedirect(req.getContextPath() + "/login");
	}
}