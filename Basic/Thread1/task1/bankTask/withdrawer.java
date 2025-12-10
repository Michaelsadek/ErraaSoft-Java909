package Basic.Thread1.task1.bankTask;

public class withdrawer extends Thread{
    private BankAcount account;

    public withdrawer(BankAcount acount){
        this.account = acount;
    }

    public void run(){
        while (true) {
            account.withdraw(80);
            try{
                Thread.sleep(1500);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
}
