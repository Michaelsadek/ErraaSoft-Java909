package EWalletSystem.src.main.service.impl;

import EWalletSystem.src.main.helper.AccountResult;
import EWalletSystem.src.main.model.Account;
import EWalletSystem.src.main.service.AccountService;
import EWalletSystem.src.main.service.AccountValidationService;
import EWalletSystem.src.main.model.EWalletSystem;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private EWalletSystem eWalletSystem = new EWalletSystem();
    private AccountValidationServiceImpl validationService = new AccountValidationServiceImpl();

    @Override
    public boolean createAccount(Account account) {
        // Check if username already exists
        if (isUsernameExists(account.getUserName())) {
            System.out.println("Username already exists!");
            return false;
        }
        
        // Check if phone number already exists
        if (isPhoneNumberExists(account.getPhoneNumber())) {
            System.out.println("Phone number already registered!");
            return false;
        }
        
        // Validate age
        if (!validationService.validateAge(account.getAge())) {
            System.out.println("You must be at least 18 years old!");
            return false;
        }
        
        // Validate phone number format
        if (!validationService.validatePhoneNumber(account.getPhoneNumber())) {
            System.out.println("Invalid Egyptian phone number format! Use +2010XXXXXXXX format");
            return false;
        }
        
        eWalletSystem.getAccounts().add(account);
        System.out.println("Account created successfully!");
        return true;
    }

    @Override
    public boolean getAccountByUserNameAndPassword(Account account) {
        return eWalletSystem.getAccounts().stream()
                .anyMatch(acc -> acc.getUserName().equals(account.getUserName()) &&
                                 acc.getPassword().equals(account.getPassword()));
    }

    @Override
    public Account getAccountByUserName(Account account) {
        Optional<Account> optionalAccount = getOptionalAccountByUserName(account);
        return optionalAccount.orElse(null);
    }

    @Override
    public AccountResult deposit(Account account, double amount) {
        Optional<Account> optionalAccount = getOptionalAccountByUserName(account);
        if (optionalAccount.isEmpty()) {
            return new AccountResult(1); // Account not exist
        }

        // Validate amount > 0
        if (amount <= 0) {
            return new AccountResult(2); // Invalid amount
        }

        Account accountToDeposit = optionalAccount.get();
        accountToDeposit.setBalance(accountToDeposit.getBalance() + amount);
        return new AccountResult(3, accountToDeposit.getBalance()); // Success
    }

    @Override
    public AccountResult withdraw(Account account, double amount) {
        Optional<Account> optionalAccount = getOptionalAccountByUserName(account);
        if (optionalAccount.isEmpty()) {
            return new AccountResult(1); // Account not exist
        }

        // Validate amount > 0
        if (amount <= 0) {
            return new AccountResult(2); // Invalid amount
        }

        Account accountWithDraw = optionalAccount.get();

        if (accountWithDraw.getBalance() < amount) {
            return new AccountResult(3); // Insufficient balance
        }

        accountWithDraw.setBalance(accountWithDraw.getBalance() - amount);
        return new AccountResult(4, accountWithDraw.getBalance()); // Success
    }

    @Override
    public AccountResult transfer(Account fromAccount, String toUserName, double amount) {
        // Validate amount
        if (amount <= 0) {
            return new AccountResult(2); // Invalid amount
        }
        
        // Get sender account
        Optional<Account> senderOptional = getOptionalAccountByUserName(fromAccount);
        if (senderOptional.isEmpty()) {
            return new AccountResult(1); // Sender not found
        }
        Account sender = senderOptional.get();
        
        // Get receiver account
        Optional<Account> receiverOptional = eWalletSystem.getAccounts().stream()
                .filter(acc -> acc.getUserName().equals(toUserName))
                .findFirst();
        if (receiverOptional.isEmpty()) {
            return new AccountResult(5); // Receiver not found
        }
        Account receiver = receiverOptional.get();
        
        // Prevent transfer to yourself
        if (sender.getUserName().equals(receiver.getUserName())) {
            return new AccountResult(6); // Cannot transfer to yourself
        }
        
        // Check sufficient balance
        if (sender.getBalance() < amount) {
            return new AccountResult(3); // Insufficient balance
        }
        
        // Perform transfer
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        
        return new AccountResult(4, sender.getBalance()); // Success
    }

    @Override
    public AccountResult changePassword(Account account, String oldPassword, String newPassword) {
        Optional<Account> optionalAccount = getOptionalAccountByUserName(account);
        if (optionalAccount.isEmpty()) {
            return new AccountResult(1); // Account not found
        }
        
        Account acc = optionalAccount.get();
        
        // Check old password matches
        if (!acc.getPassword().equals(oldPassword)) {
            return new AccountResult(2); // Wrong old password
        }
        
        // Prevent using same password
        if (oldPassword.equals(newPassword)) {
            return new AccountResult(3); // Same as old password
        }
        
        // Validate new password format
        if (!validationService.validatePassword(newPassword)) {
            return new AccountResult(4); // Invalid new password format
        }
        
        // Update password
        acc.setPassword(newPassword);
        return new AccountResult(5); // Success
    }

    private Optional<Account> getOptionalAccountByUserName(Account account) {
        return eWalletSystem.getAccounts().stream()
                .filter(acc -> acc.getUserName().equals(account.getUserName()))
                .findFirst();
    }

    private boolean isUsernameExists(String userName) {
        return eWalletSystem.getAccounts().stream()
                .anyMatch(acc -> acc.getUserName().equals(userName));
    }
    
    private boolean isPhoneNumberExists(String phoneNumber) {
        return eWalletSystem.getAccounts().stream()
                .anyMatch(acc -> acc.getPhoneNumber() != null && 
                                 acc.getPhoneNumber().equals(phoneNumber));
    }
}