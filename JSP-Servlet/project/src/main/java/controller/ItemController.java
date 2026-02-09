package controller;


import model.Item;
import model.ItemDetails;
import service.ItemDetailsService;
import service.ItemService;
import service.impl.ItemDetailsServiceImpl;
import service.impl.ItemServiceImpl;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.Item;
import model.ItemDetails;

import java.util.Optional;

@WebServlet("/ItemController")
public class ItemController extends HttpServlet {
	
	private ItemService itemService;
	private ItemDetailsService detailsService;
	
	@Override
	public void init() throws ServletException {
		this.itemService = new ItemServiceImpl();
		this.detailsService = new ItemDetailsServiceImpl();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		
		Long userId = (Long) Optional.ofNullable(req.getSession(false))
			.map(s -> s.getAttribute("userId"))
			.orElse(null);
		
		if (userId == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		
		String action = Optional.ofNullable(req.getParameter("action"))
								.filter(a -> !a.isEmpty())
								.orElse("show-items");
		
		switch (action) {
			case "add-item":
				addItem(req, resp, userId);
				break;
			case "update-item":
				updateItem(req, resp);
				break;
			case "remove-item":
			case "delete-item":
				removeItem(req, resp);
				break;
			case "show-item":
				showItem(req, resp);
				break;
			case "show-details":
				showDetailsForm(req, resp);
				break;
			case "add-details":
				addDetails(req, resp);
				break;
			case "delete-details":
				deleteDetails(req, resp);
				break;
			default:
				showItems(req, resp);
		}
	}
	
	private void showItems(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		req.setAttribute("allItems", itemService.getItemsWithDetails());
		transferFlashMessages(req);
		req.getRequestDispatcher("/item/show-items.jsp").forward(req, resp);
	}
	
	private void showItem(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Long id = parseLong(req.getParameter("id"));
		
		if (id == null) {
			setFlash(req, "errorMessage", "Invalid item ID");
			resp.sendRedirect(req.getContextPath() + "/ItemController");
			return;
		}
		
		Optional<Item> itemOpt = itemService.getItemWithDetails(id);
		
		if (itemOpt.isPresent()) {
			req.setAttribute("item", itemOpt.get());
			req.getRequestDispatcher("/item/update-item.jsp").forward(req, resp);
		} else {
			setFlash(req, "errorMessage", "Item not found");
			resp.sendRedirect(req.getContextPath() + "/ItemController");
		}
	}
	
	private void addItem(HttpServletRequest req, HttpServletResponse resp, Long userId) 
			throws IOException, ServletException {
		String name = req.getParameter("name");
		Double price = parseDouble(req.getParameter("price"));
		Integer totalNumber = parseInt(req.getParameter("totalNumber"));
		
		if (name == null || price == null || totalNumber == null) {
			setFlash(req, "errorMessage", "Invalid input data");
			resp.sendRedirect(req.getContextPath() + "/item/add-item.jsp");
			return;
		}
		
		if (itemService.getItemByName(name).isPresent()) {
			setFlash(req, "errorMessage", "Item name already exists in the system");
			resp.sendRedirect(req.getContextPath() + "/item/add-item.jsp");
			return;
		}
		
		Item item = new Item(name, price, totalNumber);
		item.setCreatedBy(userId);
		
		if (itemService.createItem(item)) {
			setFlash(req, "successMessage", "Item added successfully");
		} else {
			setFlash(req, "errorMessage", "Failed to add item");
		}
		resp.sendRedirect(req.getContextPath() + "/ItemController");
	}
	
	private void updateItem(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		Long id = parseLong(req.getParameter("id"));
		String name = req.getParameter("name");
		Double price = parseDouble(req.getParameter("price"));
		Integer totalNumber = parseInt(req.getParameter("totalNumber"));
		
		if (id == null || name == null || price == null || totalNumber == null) {
			setFlash(req, "errorMessage", "Invalid input data");
			resp.sendRedirect(req.getContextPath() + "/ItemController");
			return;
		}
		
		Optional<Item> existingOpt = itemService.getItemByName(name);
		if (existingOpt.isPresent() && !existingOpt.get().getId().equals(id)) {
			setFlash(req, "errorMessage", "Item name already exists in the system");
			resp.sendRedirect(req.getContextPath() + "/ItemController?action=show-item&id=" + id);
			return;
		}
		
		Item item = new Item(name, price, totalNumber);
		item.setId(id);
		itemService.updateItem(item);
		
		String description = req.getParameter("description");
		LocalDate issueDate = parseDate(req.getParameter("issueDate"));
		LocalDate expiryDate = parseDate(req.getParameter("expiryDate"));
		
		if (description != null && issueDate != null && expiryDate != null) {
			ItemDetails details = new ItemDetails(id, description, issueDate, expiryDate);
			if (detailsService.hasDetails(id)) {
				detailsService.updateDetails(details);
			} else {
				detailsService.createDetails(details);
			}
		}
		
		setFlash(req, "successMessage", "Item updated successfully");
		resp.sendRedirect(req.getContextPath() + "/ItemController");
	}
	
	private void removeItem(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		Long id = parseLong(req.getParameter("id"));
		if (id != null) {
			detailsService.deleteDetailsByItemId(id);
			itemService.removeItem(id);
			setFlash(req, "successMessage", "Item deleted successfully");
		}
		resp.sendRedirect(req.getContextPath() + "/ItemController");
	}
	
	private void showDetailsForm(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		Long id = parseLong(req.getParameter("id"));
		if (id != null) {
			req.setAttribute("itemId", id);
			req.getRequestDispatcher("/item/add-details.jsp").forward(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/ItemController");
		}
	}
	
	private void addDetails(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		Long itemId = parseLong(req.getParameter("itemId"));
		String description = req.getParameter("description");
		LocalDate issueDate = parseDate(req.getParameter("issueDate"));
		LocalDate expiryDate = parseDate(req.getParameter("expiryDate"));
		
		if (itemId == null || description == null || issueDate == null || expiryDate == null) {
			setFlash(req, "errorMessage", "Invalid details data");
			resp.sendRedirect(req.getContextPath() + "/ItemController");
			return;
		}
		
		if (detailsService.hasDetails(itemId)) {
			setFlash(req, "errorMessage", "Details already exist for this item");
		} else {
			ItemDetails details = new ItemDetails(itemId, description, issueDate, expiryDate);
			if (detailsService.createDetails(details)) {
				setFlash(req, "successMessage", "Item details added successfully");
			} else {
				setFlash(req, "errorMessage", "Failed to add details");
			}
		}
		resp.sendRedirect(req.getContextPath() + "/ItemController");
	}
	
	private void deleteDetails(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException {
		Long itemId = parseLong(req.getParameter("id"));
		if (itemId != null) {
			if (detailsService.deleteDetailsByItemId(itemId)) {
				setFlash(req, "successMessage", "Item details deleted successfully");
			} else {
				setFlash(req, "errorMessage", "Failed to delete details");
			}
		}
		resp.sendRedirect(req.getContextPath() + "/ItemController");
	}
	
	private void setFlash(HttpServletRequest req, String key, String message) {
		req.getSession().setAttribute(key, message);
	}
	
	private void transferFlashMessages(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Optional.ofNullable(session.getAttribute("successMessage"))
			.ifPresent(msg -> {
				req.setAttribute("successMessage", msg);
				session.removeAttribute("successMessage");
			});
		Optional.ofNullable(session.getAttribute("errorMessage"))
			.ifPresent(msg -> {
				req.setAttribute("errorMessage", msg);
				session.removeAttribute("errorMessage");
			});
	}
	
	private Long parseLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	private Double parseDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	private Integer parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	private LocalDate parseDate(String value) {
		try {
			return LocalDate.parse(value);
		} catch (Exception e) {
			return null;
		}
	}
}
