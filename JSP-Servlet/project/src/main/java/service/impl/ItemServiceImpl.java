package service.impl;

import config.DatabaseConfig;
import model.Item;
import model.ItemDetails;
import service.ItemService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemServiceImpl implements ItemService {
	
	@Override
	public List<Item> getItemsWithDetails() {
		String sql = "SELECT i.*, d.id as detail_id, d.description, d.issue_date, d.expiry_date " +
					 "FROM ITEM i LEFT JOIN ITEM_DETAILS d ON i.id = d.item_id " +
					 "WHERE i.is_deleted = 0 OR i.is_deleted IS NULL ORDER BY i.id";
		List<Item> items = new ArrayList<>();
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				items.add(mapItemWithDetails(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	@Override
	public Optional<Item> getItemWithDetails(Long id) {
		String sql = "SELECT i.*, d.id as detail_id, d.description, d.issue_date, d.expiry_date " +
					 "FROM ITEM i LEFT JOIN ITEM_DETAILS d ON i.id = d.item_id " +
					 "WHERE i.id = ? AND (i.is_deleted = 0 OR i.is_deleted IS NULL)";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return Optional.of(mapItemWithDetails(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<Item> getItemByName(String name) {
		String sql = "SELECT * FROM ITEM WHERE UPPER(name) = UPPER(?) AND (is_deleted = 0 OR is_deleted IS NULL)";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return Optional.of(mapItem(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	@Override
	public Boolean createItem(Item item) {
		String sql = "INSERT INTO ITEM (name, price, total_number, is_deleted, created_by) VALUES (?, ?, ?, 0, ?)";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, item.getName());
			stmt.setDouble(2, item.getPrice());
			stmt.setInt(3, item.getTotalNumber());
			stmt.setObject(4, item.getCreatedBy());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Boolean updateItem(Item item) {
		String sql = "UPDATE ITEM SET name = ?, price = ?, total_number = ? WHERE id = ?";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, item.getName());
			stmt.setDouble(2, item.getPrice());
			stmt.setInt(3, item.getTotalNumber());
			stmt.setLong(4, item.getId());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Boolean removeItem(Long id) {
		String sql = "UPDATE ITEM SET is_deleted = 1 WHERE id = ?";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setLong(1, id);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private Item mapItem(ResultSet rs) throws SQLException {
		Item item = new Item();
		item.setId(rs.getLong("id"));
		item.setName(rs.getString("name"));
		item.setPrice(rs.getDouble("price"));
		item.setTotalNumber(rs.getInt("total_number"));
		item.setDeleted(rs.getInt("is_deleted") == 1);
		item.setCreatedBy(rs.getObject("created_by", Long.class));
		return item;
	}
	
	private Item mapItemWithDetails(ResultSet rs) throws SQLException {
		Item item = mapItem(rs);
		Long detailId = rs.getObject("detail_id", Long.class);
		if (detailId != null) {
			ItemDetails details = new ItemDetails();
			details.setId(detailId);
			details.setItemId(item.getId());
			details.setDescription(rs.getString("description"));
			details.setIssueDate(rs.getDate("issue_date").toLocalDate());
			details.setExpiryDate(rs.getDate("expiry_date").toLocalDate());
			item.setDetails(details);
		}
		return item;
	}
}