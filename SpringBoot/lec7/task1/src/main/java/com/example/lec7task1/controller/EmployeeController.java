package com.example.lec7task1.controller;

import com.example.lec7task1.entity.Employee;
import com.example.lec7task1.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/by-ids")
    public List<Employee> getEmployeesByIds(@RequestParam List<Long> ids) {
        return employeeService.getEmployeesByIds(ids);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Employee> saveEmployees(@Valid @RequestBody List<@Valid Employee> employees) {
        return employeeService.saveEmployees(employees);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    @PutMapping("/bulk")
    public List<Employee> updateEmployees(@Valid @RequestBody List<@Valid Employee> employees) {
        return employeeService.updateEmployees(employees);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllEmployees() {
        employeeService.deleteAllEmployees();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/by-ids")
    public ResponseEntity<Void> deleteEmployeesByIds(@RequestParam List<Long> ids) {
        employeeService.deleteEmployeesByIds(ids);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/function")
    public List<Employee> searchByNameUsingFunctionName(@RequestParam String prefix) {
        return employeeService.searchByNameUsingFunctionName(prefix);
    }

    @GetMapping("/search/jpql")
    public List<Employee> searchByNameUsingJpql(@RequestParam String prefix) {
        return employeeService.searchByNameUsingJpql(prefix);
    }

    @GetMapping("/search/native")
    public List<Employee> searchByNameUsingNativeQuery(@RequestParam String prefix) {
        return employeeService.searchByNameUsingNativeQuery(prefix);
    }
}
