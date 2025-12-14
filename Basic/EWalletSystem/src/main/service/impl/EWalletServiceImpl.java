package EWalletSystem.src.main.service.impl;

import EWalletSystem.src.main.helper.AccountResult;
import EWalletSystem.src.main.model.Account;
import EWalletSystem.src.main.service.AccountService;
import EWalletSystem.src.main.service.AccountValidationService;
import EWalletSystem.src.main.service.ApplicationService;

import java.util.Objects;
import java.util.Scanner;

public class EWalletServiceImpl implements ApplicationService {
    private AccountService accountService = new AccountServiceImpl();
    private AccountValidationService accountValidationService = (AccountValidationService) new AccountValidationServiceImpl();

    @Override
    public void startApp() {
        System.out.println("\nWelcome to E-Wallet System!");
        boolean isExist = false;
        int counter = 0;
        
        while (true) {
            System.out.println("\n========== Main Menu ==========");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.println("==============================");
            System.out.print("Please choose an option: ");
            
            try {
                Scanner scanner = new Scanner(System.in);
                int service = scanner.nextInt();
                
                switch (service) {
                    case 1:
                        login();
                        break;
                    case 2:
                        signup();
                        break;
                    case 3:
                        System.out.println("\nThank you for using E-Wallet System! Have a nice day!");
                        isExist = true;
                        break;
                    default:
                        System.out.println("Invalid choice! Please choose 1-3.");
                        counter++;
                }
                
                if (isExist) break;
                
                if (counter >= 3) {
                    System.out.println("\nToo many invalid attempts. Please contact administrator.");
                    break;
                }
                
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number 1-3.");
                counter++;
                if (counter >= 3) {
                    System.out.println("\nToo many invalid attempts. Please contact administrator.");
                    break;
                }
            }
        }
    }

    private void login() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        
        while (attempts < MAX_ATTEMPTS) {
            System.out.println("\n========== Login ==========");
            Account account = getAccount(true);
            
            if (account == null) {
                attempts++;
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("\nInvalid credentials. Attempts left: " + (MAX_ATTEMPTS - attempts));
                }
                continue;
            }
            
            // Validate input not empty
            if (account.getUserName() == null || account.getUserName().trim().isEmpty() ||
                account.getPassword() == null || account.getPassword().trim().isEmpty()) {
                System.out.println("Username and password cannot be empty!");
                attempts++;
                continue;
            }
            
            boolean loginSuccess = accountService.getAccountByUserNameAndPassword(account);
            if (loginSuccess) {
                System.out.println("\nSuccessfully logged in!");
                profile(account);
                return;
            } else {
                attempts++;
                if (attempts < MAX_ATTEMPTS) {
                    System.out.println("\nInvalid username or password. Attempts left: " + (MAX_ATTEMPTS - attempts));
                }
            }
        }
        
        System.out.println("\nMaximum login attempts reached. Please try again later.");
    }

    private void signup() {
        System.out.println("\n========== Sign Up ==========");
        Account account = getAccount(false);
        
        if (account == null) {
            return;
        }
        
        boolean isAccountCreated = accountService.createAccount(account);
        
        if (isAccountCreated) {
            System.out.println("\nAccount created successfully!");
            profile(account);
        } else {
            System.out.println("\nAccount creation failed. Please try again.");
        }
    }

    private Account getAccount(boolean login) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Please enter your username (First letter uppercase): ");
        String userName = sc.next();
        
        if (!accountValidationService.validateUserName(userName)) {
            System.out.println("Invalid username! Must be at least 3 characters, first letter uppercase, and only contain letters, numbers, and underscores.");
            return null;
        }
        
        System.out.print("Please enter your password (8+ chars, mix of upper/lower/digit/special): ");
        String password = sc.next();
        
        if (!accountValidationService.validatePassword(password)) {
            System.out.println("Invalid password! Must be at least 8 characters with uppercase, lowercase, digit, and special character.");
            return null;
        }
        
        if (login) {
            return new Account(userName, password);
        }
        
        System.out.print("Please enter your phone number (+2010XXXXXXXX): ");
        String phoneNumber = sc.next();
        
        System.out.print("Please enter your address: ");
        sc.nextLine(); // Clear buffer
        String address = sc.nextLine();
        
        System.out.print("Please enter your age: ");
        float age = 0;
        try {
            age = sc.nextFloat();
        } catch (Exception e) {
            System.out.println("Invalid age format!");
            return null;
        }
        
        if (!accountValidationService.validateAge(age)) {
            System.out.println("You must be at least 18 years old!");
            return null;
        }
        
        return new Account(userName, password, phoneNumber, address, age);
    }

    private void profile(Account account) {
        boolean logout = false;
        int invalidCounter = 0;
        
        while (true) {
            System.out.println("\n========== Welcome, " + account.getUserName() + " ==========");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Show Account Details");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");
            System.out.println("==================================");
            
            System.out.print("Please choose an option: ");
            
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        deposit(account);
                        break;
                    case 2:
                        withdraw(account);
                        break;
                    case 3:
                        transfer(account);
                        break;
                    case 4:
                        showAccountDetails(account);
                        break;
                    case 5:
                        changePassword(account);
                        break;
                    case 6:
                        System.out.println("\nGoodbye! Have a nice day!");
                        logout = true;
                        break;
                    default:
                        System.out.println("Invalid option! Please choose 1-6.");
                        invalidCounter++;
                }
                
                if (logout) break;
                
                if (invalidCounter >= 3) {
                    System.out.println("\nToo many invalid attempts. Please contact admin.");
                    break;
                }
                
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number 1-6.");
                invalidCounter++;
            }
        }
    }

    private void showAccountDetails(Account account) {
        Account accountExist = accountService.getAccountByUserName(account);
        
        if (Objects.isNull(accountExist)) {
            System.out.println("Account does not exist!");
            return;
        }
        
        System.out.println(accountExist);
    }

    private void withdraw(Account account) {
        System.out.print("\nEnter amount to withdraw: ");
        
        try {
            Scanner scanner = new Scanner(System.in);
            double amount = scanner.nextDouble();
            
            if (amount <= 0) {
                System.out.println("Amount must be greater than 0!");
                return;
            }
            
            AccountResult result = accountService.withdraw(account, amount);
            
            switch (result.getError()) {
                case 1:
                    System.out.println("Account not found!");
                    break;
                case 2:
                    System.out.println("Invalid amount! Must be greater than 0.");
                    break;
                case 3:
                    Account acc = accountService.getAccountByUserName(account);
                    System.out.println("Insufficient balance! Your balance: " + 
                                     String.format("%.2f", acc.getBalance()) + " EGP");
                    break;
                case 4:
                    System.out.println("Withdrawal successful! New balance: " + 
                                     String.format("%.2f", result.getAmount()) + " EGP");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    private void deposit(Account account) {
        System.out.print("\nEnter amount to deposit: ");
        
        try {
            Scanner scanner = new Scanner(System.in);
            double amount = scanner.nextDouble();
            
            if (amount <= 0) {
                System.out.println("Amount must be greater than 0!");
                return;
            }
            
            AccountResult result = accountService.deposit(account, amount);
            
            switch (result.getError()) {
                case 1:
                    System.out.println("Account not found!");
                    break;
                case 2:
                    System.out.println("Invalid amount! Must be greater than 0.");
                    break;
                case 3:
                    System.out.println("Deposit successful! New balance: " + 
                                     String.format("%.2f", result.getAmount()) + " EGP");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a valid number.");
        }
    }

    private void transfer(Account account) {
        System.out.print("\nEnter recipient username: ");
        Scanner scanner = new Scanner(System.in);
        String toUserName = scanner.next();
        
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        
        if (amount <= 0) {
            System.out.println("Amount must be greater than 0!");
            return;
        }
        
        AccountResult result = accountService.transfer(account, toUserName, amount);
        
        switch (result.getError()) {
            case 1:
                System.out.println("Sender account not found!");
                break;
            case 2:
                System.out.println("Invalid amount! Must be greater than 0.");
                break;
            case 3:
                System.out.println("Insufficient balance!");
                break;
            case 4:
                System.out.println("Transfer successful!");
                System.out.println("Your new balance: " + 
                                 String.format("%.2f", result.getAmount()) + " EGP");
                break;
            case 5:
                System.out.println("Recipient account not found!");
                break;
            case 6:
                System.out.println("Cannot transfer to yourself!");
                break;
        }
    }

    private void changePassword(Account account) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n========== Change Password ==========");
        System.out.print("Enter old password: ");
        String oldPassword = scanner.next();
        
        System.out.print("Enter new password: ");
        String newPassword = scanner.next();
        
        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.next();
        
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return;
        }
        
        AccountResult result = accountService.changePassword(account, oldPassword, newPassword);
        
        switch (result.getError()) {
            case 1:
                System.out.println("Account not found!");
                break;
            case 2:
                System.out.println("Wrong old password!");
                break;
            case 3:
                System.out.println("New password cannot be same as old password!");
                break;
            case 4:
                System.out.println("New password must contain:");
                System.out.println("   - At least 8 characters");
                System.out.println("   - Uppercase and lowercase letters");
                System.out.println("   - At least one digit");
                System.out.println("   - At least one special character (@#$%^&+=!)");
                break;
            case 5:
                System.out.println("Password changed successfully!");
                break;
        }
    }
}