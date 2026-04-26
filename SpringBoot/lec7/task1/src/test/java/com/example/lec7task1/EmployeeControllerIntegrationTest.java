package com.example.lec7task1;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.lec7task1.entity.Employee;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerIntegrationTest {

    private static final ParameterizedTypeReference<List<Employee>> EMPLOYEE_LIST_RESPONSE =
            new ParameterizedTypeReference<>() {
            };

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void employeeCrudAndSearchApisWork() {
        Employee firstEmployee = new Employee(null, "Ahmed Adel", 25, "0100000001");
        ResponseEntity<Employee> savedEmployeeResponse =
                restTemplate.postForEntity("/employees", firstEmployee, Employee.class);

        assertThat(savedEmployeeResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(savedEmployeeResponse.getBody()).isNotNull();
        assertThat(savedEmployeeResponse.getBody().getId()).isNotNull();

        List<Employee> employeesToCreate = List.of(
                new Employee(null, "Ahmed Ali", 30, "0100000002"),
                new Employee(null, "Mona Hassan", 28, "0100000003")
        );

        ResponseEntity<List<Employee>> savedEmployeesResponse = restTemplate.exchange(
                "/employees/bulk",
                HttpMethod.POST,
                new HttpEntity<>(employeesToCreate),
                EMPLOYEE_LIST_RESPONSE
        );

        assertThat(savedEmployeesResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(savedEmployeesResponse.getBody()).hasSize(2);

        Long firstId = savedEmployeeResponse.getBody().getId();
        Long secondId = savedEmployeesResponse.getBody().get(0).getId();
        Long thirdId = savedEmployeesResponse.getBody().get(1).getId();

        ResponseEntity<List<Employee>> allEmployeesResponse = restTemplate.exchange(
                "/employees",
                HttpMethod.GET,
                null,
                EMPLOYEE_LIST_RESPONSE
        );

        assertThat(allEmployeesResponse.getBody()).hasSize(3);

        String ids = List.of(firstId, secondId).stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        ResponseEntity<List<Employee>> employeesByIdsResponse = restTemplate.exchange(
                "/employees/by-ids?ids=" + ids,
                HttpMethod.GET,
                null,
                EMPLOYEE_LIST_RESPONSE
        );

        assertThat(employeesByIdsResponse.getBody()).hasSize(2);

        Employee updatedFirstEmployee = new Employee(null, "Ahmed Saad", 26, "0100000011");
        ResponseEntity<Employee> updatedEmployeeResponse = restTemplate.exchange(
                "/employees/" + firstId,
                HttpMethod.PUT,
                new HttpEntity<>(updatedFirstEmployee),
                Employee.class
        );

        assertThat(updatedEmployeeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedEmployeeResponse.getBody()).isNotNull();
        assertThat(updatedEmployeeResponse.getBody().getName()).isEqualTo("Ahmed Saad");

        List<Employee> employeesToUpdate = List.of(
                new Employee(secondId, "Ahmed Gamal", 31, "0100000022"),
                new Employee(thirdId, "Salma Hassan", 29, "0100000033")
        );

        ResponseEntity<List<Employee>> updatedEmployeesResponse = restTemplate.exchange(
                "/employees/bulk",
                HttpMethod.PUT,
                new HttpEntity<>(employeesToUpdate),
                EMPLOYEE_LIST_RESPONSE
        );

        assertThat(updatedEmployeesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updatedEmployeesResponse.getBody()).hasSize(2);

        ResponseEntity<List<Employee>> functionSearchResponse = restTemplate.exchange(
                "/employees/search/function?prefix=Ahmed",
                HttpMethod.GET,
                null,
                EMPLOYEE_LIST_RESPONSE
        );
        ResponseEntity<List<Employee>> jpqlSearchResponse = restTemplate.exchange(
                "/employees/search/jpql?prefix=Ahmed%",
                HttpMethod.GET,
                null,
                EMPLOYEE_LIST_RESPONSE
        );
        ResponseEntity<List<Employee>> nativeSearchResponse = restTemplate.exchange(
                "/employees/search/native?prefix=Ahmed",
                HttpMethod.GET,
                null,
                EMPLOYEE_LIST_RESPONSE
        );

        assertThat(functionSearchResponse.getBody()).extracting(Employee::getName)
                .containsExactlyInAnyOrder("Ahmed Saad", "Ahmed Gamal");
        assertThat(jpqlSearchResponse.getBody()).extracting(Employee::getName)
                .containsExactlyInAnyOrder("Ahmed Saad", "Ahmed Gamal");
        assertThat(nativeSearchResponse.getBody()).extracting(Employee::getName)
                .containsExactlyInAnyOrder("Ahmed Saad", "Ahmed Gamal");

        ResponseEntity<Void> deleteByIdsResponse = restTemplate.exchange(
                "/employees/by-ids?ids=" + firstId + "," + secondId,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(deleteByIdsResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Void> deleteByIdResponse = restTemplate.exchange(
                "/employees/" + thirdId,
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(deleteByIdResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<Void> deleteAllResponse = restTemplate.exchange(
                "/employees",
                HttpMethod.DELETE,
                null,
                Void.class
        );

        assertThat(deleteAllResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<List<Employee>> emptyEmployeesResponse = restTemplate.exchange(
                "/employees",
                HttpMethod.GET,
                null,
                EMPLOYEE_LIST_RESPONSE
        );

        assertThat(emptyEmployeesResponse.getBody()).isEmpty();
    }
}
