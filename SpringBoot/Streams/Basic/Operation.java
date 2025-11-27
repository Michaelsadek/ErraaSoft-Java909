package SpringBoot.Streams.Basic;

import java.util.*;
import java.util.stream.*;

public class Operation {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);
        List<String> names = Arrays.asList(
                "Ali", "Mona", "Ahmed", "Sara", "Amr",
                "Laila", "Kareem", "Nada", "Nour", "Samy", "", null
        );
        
        // 1. Filter: Get even numbers
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
                System.out.println("Even Numbers: " + evenNumbers);

        //2. Name starts with 'A'
        List<String> namesStartingWithA = names.stream()
                .filter(n -> n != null && n.startsWith("A"))
                .collect(Collectors.toList());
                System.out.println("Names starting with A: " + namesStartingWithA);

        // 3. convert to uppercase
        List<String> upperCaseNames = names.stream()
                .filter(Objects::nonNull)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
                System.out.println("Uppercase Names: " + upperCaseNames);

        // 4 sort numbers descending
       List<Integer> sortedDescending = numbers.stream()
               .sorted(Comparator.reverseOrder())
               .collect(Collectors.toList());
                System.out.println("Sorted Descending: " + sortedDescending);

        // 5. Remove duplicates
        List<Integer> uniqueNumbers = numbers.stream()
                .distinct()
                .collect(Collectors.toList());
                System.out.println("Unique Numbers: " + uniqueNumbers);
    }

}
