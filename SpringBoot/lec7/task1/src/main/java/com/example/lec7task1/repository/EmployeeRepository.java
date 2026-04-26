package com.example.lec7task1.repository;

import com.example.lec7task1.entity.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByIdIn(List<Long> ids);

    List<Employee> findByNameStartingWithIgnoreCase(String prefix);

    @Query("select e from Employee e where lower(e.name) like lower(concat(:prefix, '%'))")
    List<Employee> searchByNameWithJpql(@Param("prefix") String prefix);

    @Query(value = "select * from employees e where lower(e.name) like lower(concat(:prefix, '%'))",
            nativeQuery = true)
    List<Employee> searchByNameWithNativeQuery(@Param("prefix") String prefix);
}
