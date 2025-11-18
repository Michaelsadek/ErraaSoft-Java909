import java.util.*;
import java.util.Scanner;

public class DivideTwoNumbers{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            System.out.println("Enter first number: ");
            int num1 = sc.nextInt();
            System.out.println("Enter second number: ");
            int num2 = sc.nextInt();
            int result = num1 / num2;
            System.out.println("Result: " + result);
        }catch(ArithmeticException exception){
            System.out.println("Cannot divide by zero.");
        }catch(Exception exception){
            System.out.println("invalid input");
        }finally{
            sc.close();
        }
    }
}