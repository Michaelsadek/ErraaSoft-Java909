package EWalletSystem.src.main.service;

import EWalletSystem.src.main.helper.AccountResult;
import EWalletSystem.src.main.model.Account;


public interface AccountService {
    boolean createAccount(Account account);
    boolean getAccountByUserNameAndPassword(Account account);
    Account getAccountByUserName(Account account);
    AccountResult deposit(Account account, double amount);
    AccountResult withdraw(Account account, double amount);
    AccountResult transfer(Account fromAccount, String toUserName, double amount);
    AccountResult changePassword(Account account, String oldPassword, String newPassword);
}