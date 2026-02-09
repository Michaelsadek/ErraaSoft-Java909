package model;

public class Item {
	private Long id;
	private String name;
	private double price;
	private int totalNumber;
	private boolean deleted;
	private Long createdBy;
	private ItemDetails details;
	
	public Item() {}
	
	public Item(String name, double price, int totalNumber) {
		this.name = name;
		this.price = price;
		this.totalNumber = totalNumber;
	}
	
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public double getPrice() { return price; }
	public void setPrice(double price) { this.price = price; }
	
	public int getTotalNumber() { return totalNumber; }
	public void setTotalNumber(int totalNumber) { this.totalNumber = totalNumber; }
	
	public boolean isDeleted() { return deleted; }
	public void setDeleted(boolean deleted) { this.deleted = deleted; }
	
	public Long getCreatedBy() { return createdBy; }
	public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
	
	public ItemDetails getDetails() { return details; }
	public void setDetails(ItemDetails details) { this.details = details; }
	
	public boolean hasDetails() {
		return details != null;
	}
}