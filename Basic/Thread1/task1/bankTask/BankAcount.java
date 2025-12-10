package Basic.Thread1.task1.bankTask;

public class BankAcount {
    private int balance = 0;

    public synchronized void deposit(int amount){
        balance += amount;
        System.out.println("Deposited: " + amount + " | Balance: " + balance);
        notify();
    }
    public synchronized void withdraw(int amount) {
        while(balance < amount){
            System.out.println("Balance too low. waiting........,,,,");
            try{
                wait();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        balance -= amount;
        System.out.println("Withdrew: " + amount + " | Balance: " + balance);
    }
    
}
