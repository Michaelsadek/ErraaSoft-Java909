package com.example.lec7task1.service;

import com.example.lec7task1.entity.Employee;
import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    List<Employee> getEmployeesByIds(List<Long> ids);

    Employee saveEmployee(Employee employee);

    List<Employee> saveEmployees(List<Employee> employees);

    Employee updateEmployee(Long id, Employee employee);

    List<Employee> updateEmployees(List<Employee> employees);

    void deleteAllEmployees();

    void deleteEmployeeById(Long id);

    void deleteEmployeesByIds(List<Long> ids);

    List<Employee> searchByNameUsingFunctionName(String prefix);

    List<Employee> searchByNameUsingJpql(String prefix);

    List<Employee> searchByNameUsingNativeQuery(String prefix);
}
