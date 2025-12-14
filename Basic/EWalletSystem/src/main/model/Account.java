package EWalletSystem.src.main.model;

public class Account {
    private String userName;
    private String password;
    private double balance;
    private String phoneNumber;
    private String address;
    private float age;

    public Account() {
    }

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Account(String userName, String password, String phoneNumber, String address, float age) {
        this.userName = userName;
        this.password = password;
        this.balance = 0;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "\n----- Account Information -----\n" +
               "User Name   : " + userName + "\n" +
               "Password    : ********\n" +
               "Balance     : " + String.format("%.2f", balance) + " EGP\n" +
               "Phone Number: " + phoneNumber + "\n" +
               "Address     : " + address + "\n" +
               "Age         : " + (int)age + "\n" +
               "--------------------------------\n";
    }
}