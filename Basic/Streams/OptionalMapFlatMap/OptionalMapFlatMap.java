package Basic.Streams.OptionalMapFlatMap;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalMapFlatMap {
static class Student {
        String name;
        String department;
        double grade;

        Student(String name, String department, double grade) {
            this.name = name;
            this.department = department;
            this.grade = grade;
        }

        public String getName() { return name; }
        public String getDepartment() { return department; }
        public double getGrade() { return grade; }
    }

    static class Employee {
        String name;
        int age;
        String department;
        double salary;

        Employee(String name, int age, String department, double salary) {
            this.name = name;
            this.age = age;
            this.department = department;
            this.salary = salary;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
        public String getDepartment() { return department; }
        public double getSalary() { return salary; }
    }

    public static void main(String[] args) {

         List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);

        List<String> names = Arrays.asList(
                "Ali", "Mona", "Ahmed", "Sara", "Amr",
                "Laila", "Kareem", "Nada", "Nour", "Samy", "", null
        );

        List<Student> students = Arrays.asList(
                new Student("Ali", "IT", 85),
                new Student("Mona", "CS", 92),
                new Student("Ahmed", "IT", 60),
                new Student("Sara", "CS", 70),
                new Student("Omar", "IS", 45),
                new Student("Laila", "IS", 78)
        );

        List<Employee> employees = Arrays.asList(
                new Employee("Ali", 30, "HR", 5000),
                new Employee("Mona", 25, "IT", 7000),
                new Employee("Ahmed", 30, "HR", 5500),
                new Employee("Sara", 27, "IT", 7200),
                new Employee("Omar", 40, "Finance", 8000),
                new Employee("Laila", 35, "Finance", 8200)
        );

        List<List<String>> nestedWords = Arrays.asList(
                Arrays.asList("Java", "Stream"),
                Arrays.asList("API", "Lambda"),
                Arrays.asList("FlatMap", "Map")
        );
    
        //1 Flatten a list of lists into a single list.
        List<String> flatListed = nestedWords.stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());
        System.out.println("Flattened list: " + flatListed);

        //2 Extract all unique characters from a list of words.
        List<Character> uniqueChars = names.stream()
            .filter(Objects::nonNull)
            .flatMap(word -> word.chars().mapToObj(c -> (char) c))
            .distinct()
            .collect(Collectors.toList());
        System.out.println("Unique characters: " + uniqueChars);

        //3 Filter a list of Optionals and collect non-empty values.
        List<Optional<String>> optionals = Arrays.asList(Optional.of("Java"), Optional.empty(), Optional.of("stream"), Optional.empty());
        List<String> nonEmpty = optionals.stream()
            .flatMap(Optional::stream)      
            .collect(Collectors.toList());
        System.out.println("Non-empty optionals: " + nonEmpty);

        //4Map a list of strings to their lengths.
        List<Integer> nameLengths = names.stream()
        .filter(Objects::nonNull)
        .map(String::length)
        .collect(Collectors.toList());
        System.out.println("Name lengths: " + nameLengths);

        //5 Return a list of uppercased words that start with “A”.
        List<String> upperA = names.stream()
            .filter(Objects::nonNull)
            .filter(w -> w.startsWith("A"))
            .map(String::toUpperCase)
            .collect(Collectors.toList());
        System.out.println("Uppercased names starting with A: " + upperA);
    }
}