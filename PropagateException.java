public class PropagateException {
    public static void methodB(){
        System.out.println("inside method throwing exception");
        int result = 10/0;
    }
    public static void methodA(){
        System.out.println("inside method calling methodB");
        methodB();
        System.out.println("exiting methodA");
    }
    public static void main(String[] args) {
        try{
            System.out.println("inside main calling methodA");
            methodA();
        }catch(ArithmeticException e){
            System.out.println("Exception caught in main:" + e.getMessage());
        }
        System.out.println("exiting main");
        
    }
    
}
