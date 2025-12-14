package EWalletSystem.src.main.service.impl;


import java.util.Objects;
import java.util.Scanner;

import EWalletSystem.src.main.helper.AccountResult;
import EWalletSystem.src.main.model.Account;
import EWalletSystem.src.main.service.ApplicationService;

public class EWalletServiceImpl implements ApplicationService {

    private AccountServiceImpl accountService = new AccountServiceImpl();

    private AccountValidationServiceImpl accountValidationService = new AccountValidationServiceImpl();
    @Override
    public void startApp() {
        System.out.println("welcome sir :)");
        boolean isExist = false;
        int counter = 0;
        while (true) {
            System.out.println("pls enter your service :)");
            System.out.println("1.login           2.signup             3.Exit");
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
                    System.out.println("have a nice day :)");
                    isExist = true;
                    break;
                default:
                    System.out.println("invalid choose :(");
                    counter++;
            }

            if (isExist) {
                break;
            }

            if (counter == 3) {
                System.out.println("pls contact with admin :(");
                break;
            }
        }
    }

    private void login(){
        Account account = getAccount(true);

        if (Objects.isNull(account)) {
            return;
        }
        boolean loginSuccess = accountService.getAccountByUserNameAndPassword(account);
        if (loginSuccess) {
            System.out.println("success login :)");
            profile(account);
        } else {
            System.out.println("invalid username or password :(");
        }

    }

    private void signup(){
        Account account = getAccount(false);

        if (Objects.isNull(account)) {
            return;
        }
        // service to add account on EWallet system
        boolean isAccountCreated = accountService.createAccount(account);

        if (isAccountCreated) {
            System.out.println("account created success :)");
            profile(account);
        } else {
            System.out.println("account already exist with same username " + account.getPassword());
        }
    }

    private Account getAccount(boolean login){

        Scanner sc = new Scanner(System.in);

        System.out.print("Please enter your username: ");
        String userName = sc.next();

        if (!accountValidationService.validateUserName(userName)) {
            System.out.println("invalid user name ..........");
            return null;
        }

        System.out.print("Please enter your password: ");
        String password = sc.next();

        if (!accountValidationService.validatePassword(password)) {
            System.out.println("invalid password ..........");
            return null;
        }

        if (login) {
            return new Account(userName, password);
        }

        System.out.print("Please enter your phone number: ");
        String phoneNumber = sc.next();

        System.out.print("Please enter your address: ");
        String address = sc.next();

        System.out.print("Please enter your age: ");
        float age = sc.nextFloat();

        return new Account(userName, password, phoneNumber, address, age);
    }


    private void profile(Account account){
        boolean logout = false;
        int counter = 0;
        while (true) {
            System.out.println("----------> services <------------");
            System.out.println("1.deposit     2.withdraw   3.show account details    4.logout");
            Scanner scanner = new Scanner(System.in);
            System.out.println("pls give me service you need to apply.");
            int result = scanner.nextInt();

            switch (result) {
                case 1:
                    deposit(account);
                    break;
                case 2:
                    withdraw(account);
                    break;
                case 3:
                    showAccountDetails(account);
                    break;
                case 4:
                    System.out.println("have a nice day :)");
                    logout = true;
                    break;
                default:
                    System.out.println("invalid service");
                    counter++;
            }
            if (logout){
                break;
            }

            if (counter == 3) {
                System.out.println("pls contact with admin :(");
                break;
            }
        }

    }

    private void showAccountDetails(Account account) {
        Account accountExist = accountService.getAccountByUserName(account);

        if (Objects.isNull(accountExist)) {
            System.out.println("account not exist :(");
            return;
        }

        System.out.println(account);
    }

    private void withdraw(Account account) {
        System.out.println("pls enter amount you need to deposit");
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();

        AccountResult withdrawSuccess = accountService.withdraw(account, amount);

        Integer error = withdrawSuccess.getError();
        if (error == 4) {
            System.out.println("withdraw Success your balance : " + withdrawSuccess.getAmount());
        } else if (error == 3){
            System.out.println("amount you need to withdraw greater than your Balance :(");
        } else if (error == 2) {
            System.out.println("amount must be greater than or equal 100 :(");
        } else if (error == 1) {
            System.out.println("Account not exist :(");
        }
    }

    private void deposit(Account account) {
        System.out.println("pls enter amount you need to deposit");
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();

        AccountResult depositSuccess = accountService.deposit(account, amount);

        Integer error = depositSuccess.getError();
        if (error == 3) {
            System.out.println("deposit Success your balance : " + depositSuccess.getAmount());
        } else if (error == 2){
            System.out.println("amount must be greater than or equal 100 :(");
        } else if (error == 1) {
            System.out.println("Account not exist :(");
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
                System.out.println("invalid amount! Must be greater than 0.");
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
                System.out.println("ccount not found!");
                break;
            case 2:
                System.out.println(" Wrong old password!");
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