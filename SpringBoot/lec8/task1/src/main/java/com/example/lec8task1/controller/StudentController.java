package com.example.lec8task1.controller;

import com.example.lec8task1.dto.request.UniversityRequests.CreateStudentRequest;
import com.example.lec8task1.dto.request.UniversityRequests.RegisterStudentToCourseRequest;
import com.example.lec8task1.dto.response.UniversityResponses.StudentDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.StudentSummaryResponse;
import com.example.lec8task1.service.StudentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentSummaryResponse createStudent(@Valid @RequestBody CreateStudentRequest request) {
        return studentService.createStudent(request);
    }

    @GetMapping
    public List<StudentSummaryResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDetailsResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}/courses")
    public StudentDetailsResponse registerStudentToCourse(
            @PathVariable Long id,
            @Valid @RequestBody RegisterStudentToCourseRequest request
    ) {
        return studentService.registerStudentToCourse(id, request.courseId());
    }
}
