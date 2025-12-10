package Basic.Thread1.task1.bankTask;

public class Depositor extends Thread{
    private BankAcount account;

    public Depositor(BankAcount account){
        this.account = account;
    }

    @Override
    public void run(){
        while (true) {
            account.deposit(100);
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
}
