package Basic.Thread1.task1;

class WorkThread extends Thread {
    private String name;

    WorkThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(name + " started");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " ended");
    }
}

public class JoinExample {
    public static void main(String[] args) throws InterruptedException {
        WorkThread t1 = new WorkThread("Thread 1");
        WorkThread t2 = new WorkThread("Thread 2");

        t1.start();
        t2.start();

        
        t1.join();
        t2.join();

        System.out.println("Both threads have finished execution");
    }
}