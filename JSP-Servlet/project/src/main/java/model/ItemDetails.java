package model;
import java.time.LocalDate;

public class ItemDetails {
	private Long id;
	private Long itemId;
	private String description;
	private LocalDate issueDate;
	private LocalDate expiryDate;
	
	public ItemDetails() {}
	
	public ItemDetails(Long itemId, String description, LocalDate issueDate, LocalDate expiryDate) {
		this.itemId = itemId;
		this.description = description;
		this.issueDate = issueDate;
		this.expiryDate = expiryDate;
	}
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	public Long getItemId() { return itemId; }
	public void setItemId(Long itemId) { this.itemId = itemId; }
	
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	public LocalDate getIssueDate() { return issueDate; }
	public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
	
	public LocalDate getExpiryDate() { return expiryDate; }
	public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
	
	public boolean isExpired() {
		return LocalDate.now().isAfter(expiryDate);
	}
}