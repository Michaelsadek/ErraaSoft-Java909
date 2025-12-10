package Basic.Thread1;

public class running  implements Runnable{
    
    public void run(){
        System.out.println("it running in "  + Thread.currentThread().getName());
    }
    public static void main(String[] args) {
        Thread i = new Thread(new running());
        i.start();
    }
    
}
