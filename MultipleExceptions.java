public class MultipleExceptions {
    public static void main(String[] args) {
        try {
            String text = null;
            int[] numbers = {1, 2, 3};

            System.out.println(numbers[5]);
        } catch(NullPointerException e) {
            System.out.println("Caught NullPointerException: " + e.getMessage());
        }catch(ArithmeticException e){
            System.out.println("Caught ArithmeticException: " + e.getMessage());
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        }  catch (Exception e) {
            System.out.println("Caught general exception: " + e.getMessage());
        }
    }
}
