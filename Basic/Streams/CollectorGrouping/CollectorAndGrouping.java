package Basic.Streams.CollectorGrouping;

import java.util.*;
import java.util.stream.*;

public class CollectorAndGrouping {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);
        
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
        // 1 group students by department
        Map<String, List<Student>> studentsByDepartment = students.stream()
            .collect(Collectors.groupingBy(Student::getDepartment));
            System.out.println("Students by Department: " + studentsByDepartment);
        //2 partition numbers into even and odd using partitioningBy
        Map<Boolean, List<Integer>> evenOddPartition = numbers.stream()
            .collect(Collectors.partitioningBy(n -> n % 2 == 0));
            System.out.println("Even Numbers: " + evenOddPartition.get(true));
            System.out.println("Odd Numbers: " + evenOddPartition.get(false));
        // 3 create comma-separated string from list of strings
        List<String> names = Arrays.asList("Ali", "Mona", "Ahmed", "Sara", "Omar");
        String commaSeparated = names.stream()
            .collect(Collectors.joining(", "));
            System.out.println("Comma Separated Names: " + commaSeparated);
        //4 Group employees by age and count how many per age
        Map<Integer, Long> employeesByAge = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getAge,
                Collectors.counting()
            ));
            System.out.println("Employees by Age: " + employeesByAge);
        //5 find average salary per department
        Map<String, Double> avgSalaryByDepartment = employees.stream()
            .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.averagingDouble(Employee::getSalary)
            ));
            System.out.println("Average Salary by Department: " + avgSalaryByDepartment);
    
    }
}
class Student {
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
        return name;
    }
}

class Employee {
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