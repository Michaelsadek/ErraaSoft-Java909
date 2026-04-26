package com.example.lec7task1.service;

import com.example.lec7task1.entity.Employee;
import com.example.lec7task1.exception.EmployeeNotFoundException;
import com.example.lec7task1.repository.EmployeeRepository;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByIds(List<Long> ids) {
        return employeeRepository.findByIdIn(ids);
    }

    @Override
    @Transactional
    public Employee saveEmployee(Employee employee) {
        validateEmployeeForCreate(employee);
        return employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public List<Employee> saveEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            return List.of();
        }

        employees.forEach(this::validateEmployeeForCreate);
        return employeeRepository.saveAll(employees);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = findEmployeeOrThrow(id);
        applyUpdates(existingEmployee, employee);
        return employeeRepository.save(existingEmployee);
    }

    @Override
    @Transactional
    public List<Employee> updateEmployees(List<Employee> employees) {
        if (employees.isEmpty()) {
            return List.of();
        }

        List<Long> ids = employees.stream()
                .map(Employee::getId)
                .toList();

        if (ids.stream().anyMatch(id -> id == null)) {
            throw new IllegalArgumentException("Every employee must include an id for bulk update.");
        }

        Map<Long, Employee> existingEmployees = employeeRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Employee::getId, Function.identity()));

        List<Long> missingIds = ids.stream()
                .filter(id -> !existingEmployees.containsKey(id))
                .distinct()
                .toList();

        if (!missingIds.isEmpty()) {
            throw new EmployeeNotFoundException("Employees not found for ids: " + missingIds);
        }

        List<Employee> updatedEmployees = employees.stream()
                .map(employee -> {
                    Employee existingEmployee = existingEmployees.get(employee.getId());
                    applyUpdates(existingEmployee, employee);
                    return existingEmployee;
                })
                .toList();

        return employeeRepository.saveAll(updatedEmployees);
    }

    @Override
    @Transactional
    public void deleteAllEmployees() {
        employeeRepository.deleteAll();
    }

    @Override
    @Transactional
    public void deleteEmployeeById(Long id) {
        Employee employee = findEmployeeOrThrow(id);
        employeeRepository.delete(employee);
    }

    @Override
    @Transactional
    public void deleteEmployeesByIds(List<Long> ids) {
        List<Long> missingIds = findMissingIds(ids);
        if (!missingIds.isEmpty()) {
            throw new EmployeeNotFoundException("Employees not found for ids: " + missingIds);
        }

        employeeRepository.deleteAllById(ids);
    }

    @Override
    public List<Employee> searchByNameUsingFunctionName(String prefix) {
        return employeeRepository.findByNameStartingWithIgnoreCase(normalizePrefix(prefix));
    }

    @Override
    public List<Employee> searchByNameUsingJpql(String prefix) {
        return employeeRepository.searchByNameWithJpql(normalizePrefix(prefix));
    }

    @Override
    public List<Employee> searchByNameUsingNativeQuery(String prefix) {
        return employeeRepository.searchByNameWithNativeQuery(normalizePrefix(prefix));
    }

    private Employee findEmployeeOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    private List<Long> findMissingIds(List<Long> ids) {
        List<Long> existingIds = employeeRepository.findByIdIn(ids).stream()
                .map(Employee::getId)
                .toList();

        return new LinkedHashSet<>(ids).stream()
                .filter(id -> !existingIds.contains(id))
                .toList();
    }

    private void validateEmployeeForCreate(Employee employee) {
        if (employee.getId() != null) {
            throw new IllegalArgumentException("Do not send id when creating a new employee.");
        }
    }

    private void applyUpdates(Employee existingEmployee, Employee newEmployeeData) {
        existingEmployee.setName(newEmployeeData.getName());
        existingEmployee.setAge(newEmployeeData.getAge());
        existingEmployee.setPhoneNumber(newEmployeeData.getPhoneNumber());
    }

    private String normalizePrefix(String prefix) {
        String normalizedPrefix = prefix == null ? "" : prefix.trim();
        if (normalizedPrefix.endsWith("%")) {
            normalizedPrefix = normalizedPrefix.substring(0, normalizedPrefix.length() - 1);
        }
        return normalizedPrefix;
    }
}
