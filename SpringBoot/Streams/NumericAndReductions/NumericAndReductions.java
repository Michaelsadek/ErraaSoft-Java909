package SpringBoot.Streams.NumericAndReductions;

import java.util.Arrays;
import java.util.List;

public class NumericAndReductions {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);

        //1 sum using reduce
        int sum = numbers.stream()
            .reduce(0, Integer::sum);
            System.out.println("Sum: " + sum);


        //2 maximum and minimum 
        int max = numbers.stream().max(Integer::compare).orElse(0);
        int min = numbers.stream().min(Integer::compare).orElse(0);
        System.out.println("Maximum: " + max);
        System.out.println("Minimum: " + min);


        //3 avarage of list values
        double avarage = numbers.stream()
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0);
            System.out.println("Average: " + avarage);

        //4 multiply all integers together
        int product = numbers.stream()
            .reduce(1, (a, b) -> a * b );
            System.out.println("Product: " + product);

        // 5 count how many numbers are positive
        long positiveCount = numbers.stream()
            .filter(n -> n > 0)
            .count();
            System.out.println("Positive Count: " + positiveCount);
    }
}
