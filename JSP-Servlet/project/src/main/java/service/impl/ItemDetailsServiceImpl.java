package service.impl;

import config.DatabaseConfig;
import model.ItemDetails;
import service.ItemDetailsService;
import java.sql.*;
import java.util.Optional;

public class ItemDetailsServiceImpl implements ItemDetailsService {
	
	@Override
	public Optional<ItemDetails> getDetailsByItemId(Long itemId) {
		String sql = "SELECT * FROM ITEM_DETAILS WHERE item_id = ?";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setLong(1, itemId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return Optional.of(mapDetails(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	@Override
	public Boolean createDetails(ItemDetails details) {
		String sql = "INSERT INTO ITEM_DETAILS (item_id, description, issue_date, expiry_date) VALUES (?, ?, ?, ?)";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setLong(1, details.getItemId());
			stmt.setString(2, details.getDescription());
			stmt.setDate(3, Date.valueOf(details.getIssueDate()));
			stmt.setDate(4, Date.valueOf(details.getExpiryDate()));
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Boolean updateDetails(ItemDetails details) {
		String sql = "UPDATE ITEM_DETAILS SET description = ?, issue_date = ?, expiry_date = ? WHERE item_id = ?";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, details.getDescription());
			stmt.setDate(2, Date.valueOf(details.getIssueDate()));
			stmt.setDate(3, Date.valueOf(details.getExpiryDate()));
			stmt.setLong(4, details.getItemId());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Boolean deleteDetailsByItemId(Long itemId) {
		String sql = "DELETE FROM ITEM_DETAILS WHERE item_id = ?";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setLong(1, itemId);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Boolean hasDetails(Long itemId) {
		String sql = "SELECT 1 FROM ITEM_DETAILS WHERE item_id = ?";
		try (Connection conn = DatabaseConfig.getDataSource().getConnection();
			 PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setLong(1, itemId);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private ItemDetails mapDetails(ResultSet rs) throws SQLException {
		ItemDetails details = new ItemDetails();
		details.setId(rs.getLong("id"));
		details.setItemId(rs.getLong("item_id"));
		details.setDescription(rs.getString("description"));
		details.setIssueDate(rs.getDate("issue_date").toLocalDate());
		details.setExpiryDate(rs.getDate("expiry_date").toLocalDate());
		return details;
	}
}