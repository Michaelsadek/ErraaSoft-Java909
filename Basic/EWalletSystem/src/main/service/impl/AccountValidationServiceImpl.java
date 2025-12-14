package EWalletSystem.src.main.service.impl;

import EWalletSystem.src.main.service.AccountValidationService;


public class AccountValidationServiceImpl implements AccountValidationService {
    
    @Override
    public boolean validateUserName(String userName) {
        if (userName == null || userName.length() < 3) {
            return false;
        }
        // First letter must be uppercase
        if (!Character.isUpperCase(userName.charAt(0))) {
            return false;
        }
        // Only letters, numbers, and underscores allowed
        return userName.matches("^[A-Z][A-Za-z0-9_]{2,}$");
    }

    @Override
    public boolean validatePassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        // At least one uppercase, one lowercase, one digit, one special character
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(regex);
    }
    
    @Override
    public boolean validateAge(float age) {
        return age >= 18;
    }
    
    @Override
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return false;
        // Egyptian phone number format: +20 followed by 10 or 11 digits
        String regex = "^\\+20(10|11|12|15)\\d{8}$";
        return phoneNumber.matches(regex);
    }
}