package SpringBoot.Streams.Intermediate;

import java.util.Arrays;
import java.util.List;
import java.util.*;
import java.util.stream.*;

public class Intermediate {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);
        List<String> names = Arrays.asList(
                "Ali", "Mona", "Ahmed", "Sara", "Amr",
                "Laila", "Kareem", "Nada", "Nour", "Samy", "", null
        );

        // 1. count string longer then 5 char
        long countLongerNames = names.stream()
                .filter(Objects::nonNull)
                .filter(s -> s.length() > 5)
                .count();
                System.out.println("Count of names longer than 5 characters: " + countLongerNames);


        //2.find the first number greater than 5
        Optional<Integer> firstGreaterThan5 = numbers.stream()
                .filter(n -> n > 5)
                .findFirst();
                System.out.println("First number greater than 5: " + firstGreaterThan5);


        //3. check if any number is divisible by 5
        boolean hasDivisibleBy5 = numbers.stream()
        .anyMatch(n -> n % 5 == 0);
        System.out.println("Has any number divisible by 5: " + hasDivisibleBy5);

        // 4 collect elements into a set
        Set<Integer> numberSet = numbers.stream()
                .collect(Collectors.toSet());
                System.out.println("Number Set: " + numberSet);

        // 5 skip the first 3 elements
        List<Integer> skippedThree = numbers.stream()
                .skip(3)
                .collect(Collectors.toList());
        System.out.println("List after skipping first 3 elements: " + skippedThree);
    }

}
