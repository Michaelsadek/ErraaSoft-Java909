public class NestedTryCatch {
    public static void main(String[] args){
        try {
            System.out.println("in outer try block");
            try {
                System.out.println("in inner try block");
                int result = 10/0;
                System.out.println(result);
            } catch (NullPointerException e) {
                System.out.println("inner catch block null pointer exception");
            }
            System.out.println("exiting Outer try block");
        } catch (ArithmeticException e) {
            System.out.println("outer catch block caught the arithmetic exception");
        }catch(Exception e){
            System.out.println("outer catch block caught the general exception");
        }
        System.out.println("program finished");
    }
}
