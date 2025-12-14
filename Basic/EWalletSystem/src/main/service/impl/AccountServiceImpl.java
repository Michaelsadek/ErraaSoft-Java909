package EWalletSystem.src.main.service.impl;


import java.util.Optional;

import EWalletSystem.src.main.helper.AccountResult;
import EWalletSystem.src.main.model.Account;
import EWalletSystem.src.main.model.EWalletSystem;
import EWalletSystem.src.main.service.AccountService;

public class AccountServiceImpl implements AccountService {

    private EWalletSystem eWalletSystem = new EWalletSystem();
    private AccountValidationServiceImpl validationService = new AccountValidationServiceImpl();



    /**
     * create new account
     * @param account
     * @return ture if account success - false if account already exist
     */
    @Override
    public boolean createAccount(Account account) {
          Optional<Account> existingAccount = eWalletSystem.getAccounts().stream()
                .filter(acc -> acc.getUserName().equals(account.getUserName()))
                .findFirst();
        
        if (existingAccount.isPresent()) {
            return false;
        }
        
        // Check if phone number already exists
        Optional<Account> existingPhone = eWalletSystem.getAccounts().stream()
                .filter(acc -> acc.getPhoneNumber() != null && 
                               acc.getPhoneNumber().equals(account.getPhoneNumber()))
                .findFirst();
        
        if (existingPhone.isPresent()) {
            return false;
        }
        
        // Validate age
        if (account.getAge() < 18) {
            return false;
        }
        
        // Validate phone number format (Egyptian format)
        if (account.getPhoneNumber() != null && !account.getPhoneNumber().matches("^\\+20(10|11|12|15)\\d{8}$")) {
            return false;
        }
        
        eWalletSystem.getAccounts().add(account);
        return true;

    }

    /**
     * get Account
     * @param account
     * @return
     */
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

    /**
     * deposit
     * @param account
     * @param amount
     * @return
     */
    @Override
    public AccountResult deposit(Account account, double amount) {

       Optional<Account> optionalAccount = getOptionalAccountByUserName(account);
        if (optionalAccount.isEmpty()) {
            return new AccountResult(1); // Account not exist
        }

        // validate amount >= 100
        if (amount < 100) {
            return new AccountResult(2);
        }

        Account accountToDeposit = optionalAccount.get();
        accountToDeposit.setBalance(accountToDeposit.getBalance() + amount);
        return new AccountResult(3, accountToDeposit.getBalance());
    }

    @Override
    public AccountResult withdraw(Account account, double amount) {
        Optional<Account> optionalAccount = getOptionalAccountByUserName(account);
        if (optionalAccount.isEmpty()) {
            return new AccountResult(1);
        }

        // validate amount >= 100
        if (amount < 100) {
            return new AccountResult(2);
        }

        Account accountWithDraw = optionalAccount.get();

        if (accountWithDraw.getBalance() < amount) {
            return new AccountResult(3);
        }

        accountWithDraw.setBalance(accountWithDraw.getBalance() - amount);
        return new AccountResult(4, accountWithDraw.getBalance());
    }

    /**
     * get Optional Account By UserName
     * @param account
     * @return
     */
    private Optional<Account> getOptionalAccountByUserName(Account account) {
        return eWalletSystem.getAccounts().stream()
                .filter(acc -> acc.getUserName().equals(account.getUserName()))
                .findFirst();
    }

     // Added method: Change password
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
    
     @Override
    public AccountResult transfer(Account fromAccount, String toUserName, double amount) {
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
        
        // Validate amount >= 100
        if (amount < 100) {
            return new AccountResult(2);
        }
        
        // Check sufficient balance
        if (sender.getBalance() < amount) {
            return new AccountResult(3);
        }
        
        // Perform transfer
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
        
        return new AccountResult(4, sender.getBalance()); // Success
    }
   

      /**
     * Check if username already exists
     */
    private boolean isUsernameExists(String userName) {
        return eWalletSystem.getAccounts().stream()
                .anyMatch(acc -> acc.getUserName().equals(userName));
    }
    
    /**
     * Check if phone number already exists
     */
    private boolean isPhoneNumberExists(String phoneNumber) {
        return eWalletSystem.getAccounts().stream()
                .anyMatch(acc -> acc.getPhoneNumber() != null && 
                                 acc.getPhoneNumber().equals(phoneNumber));
    }
}
