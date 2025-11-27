package SpringBoot.Streams.AdvancedOperations;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Advanced {

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

        @Override
        public String toString() {
            return name + " (" + salary + ")";
        }
    }
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

        @Override
        public String toString() {
            return name + " (" + grade + ")";
        }
    }
    public static void main(String[] args) {
         List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);

        List<String> names = Arrays.asList(
            "Ali", "Mona", "Ahmed", "Sara", "Amr",
            "Laila", "Kareem", "Nada", "Nour", "Samy", "", null
        );

        List<Employee> employees = Arrays.asList(
            new Employee("Ali", 30, "HR", 5000),
            new Employee("Mona", 25, "IT", 7000),
            new Employee("Ahmed", 30, "HR", 5500),
            new Employee("Sara", 27, "IT", 7200),
            new Employee("Omar", 40, "Finance", 8000),
            new Employee("Laila", 35, "Finance", 8200)
        );

        List<Student> students = Arrays.asList(
            new Student("Ali", "IT", 85),
            new Student("Mona", "CS", 92),
            new Student("Ahmed", "IT", 60),
            new Student("Sara", "CS", 70),
            new Student("Omar", "IS", 45),
            new Student("Laila", "IS", 78)
        );
        // 1 Sort a list of employees by salary then by name.
        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary)
                .thenComparing(Employee::getName))
                .collect(Collectors.toList());
        System.out.println("Sorted Employees: " + sortedEmployees);
        // 2 Find second highest number
        Optional<Integer> secondHighest = numbers.stream()
                .distinct()
               .sorted(Comparator.reverseOrder())
               .skip(1)
                .findFirst();
        System.out.println(secondHighest.orElse(null));
        // 3 Find duplicate elements in list
        Set<Integer> seen = new HashSet<>();
        List<Integer> duplicates = numbers.stream()
            .filter(n -> !seen.add(n))
            .collect(Collectors.toList());
        System.out.println(duplicates);
        // 4 Remove null or empty strings
        List<String> cleaned = names.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        System.out.println(cleaned);
        // Partition students into pass/fail
          Map<Boolean, List<Student>> partitioned = students.stream()
                    .collect(Collectors.partitioningBy(s -> s.getGrade() >= 60));

        System.out.println("Passed: " + partitioned.get(true));
        System.out.println("Failed: " + partitioned.get(false));
    }
}
