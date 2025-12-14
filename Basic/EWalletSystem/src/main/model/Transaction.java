package EWalletSystem.src.main.model;

import java.time.LocalDateTime;

public class Transaction {
    private String type; // DEPOSIT, WITHDRAW, TRANSFER
    private double amount;
    private LocalDateTime timestamp;
    private String description;
    private String fromAccount;
    private String toAccount;
    
    public Transaction(String type, double amount, String description, String fromAccount) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.fromAccount = fromAccount;
        this.timestamp = LocalDateTime.now();
    }
    
    public Transaction(String type, double amount, String description, String fromAccount, String toAccount) {
        this(type, amount, description, fromAccount);
        this.toAccount = toAccount;
    }
    
    // Getters
    public String getType() { 
        return type; 
    }
    public double getAmount() { 
        return amount; 
    }
    public LocalDateTime getTimestamp() { 
        return timestamp; 
    }
    public String getDescription() { 
        return description; 

    }
    public String getFromAccount() { 
        return fromAccount; 
    }
    public String getToAccount() {
         return toAccount; 
        }
    
    @Override
    public String toString() {
        String base = String.format("[%s] %s: %.2f EGP - %s", 
            timestamp.toString().replace("T", " ").substring(0, 19),
            type, amount, description);
        
        if (toAccount != null) {
            base += " | To: " + toAccount;
        }
        
        return base;
    }
}
