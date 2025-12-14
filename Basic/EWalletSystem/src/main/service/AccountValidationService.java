package EWalletSystem.src.main.service;

public interface AccountValidationService {
    boolean validateUserName(String userName);
    boolean validatePassword(String password);
    boolean validateAge(float age);
    boolean validatePhoneNumber(String phoneNumber);
}