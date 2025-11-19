import java.util.Scanner;

public class ArrayAccess {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40, 50};
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Enter index (0-4):");
            int index = sc.nextInt();
            System.out.println("Value at index " + index + ": " + numbers[index]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("index out of bounds! please enter 0-4");
        }catch (Exception e) {
            System.out.println("Invalid input");
        }finally{
            sc.close();
        }
    }
    
}
