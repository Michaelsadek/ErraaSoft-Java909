public class FinallyBlockDemo {
    public static void main(String[] args) {
        System.out.println("first try with no exception");
        runTest(false);
        System.out.println("second try with exception");
        runTest(true);
    }

    public static void runTest(boolean throwException) {
        try{
            System.out.println("inside try block");
            if (throwException) {
                System.out.println("throwing exception");
                throw new IllegalArgumentException("test exception");
            }
            System.out.println("exiting try block");
            return;
        }catch(IllegalArgumentException e){
            System.out.println("inside catch block" + e.getMessage());
            return;
        }finally{
            System.out.println("inside finally block");
        }
    }
}

