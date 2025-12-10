package Basic.Thread1.task1.bankTask;

public class BankSystem {
    public static void main(String[] args){
        BankAcount acount = new BankAcount();
        Depositor depositor = new Depositor(acount);
        withdrawer withdrawer = new withdrawer(acount);
        depositor.start();
        withdrawer.start();
    }
}
