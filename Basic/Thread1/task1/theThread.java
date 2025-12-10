package Basic.Thread1.task1;

public class theThread extends Thread{

    @Override
    public void run(){ 
        System.out.println("Hello from thread");
    }
    public static void main(String[] args) {
        theThread t = new theThread();
        t.start();
    }
    
}
