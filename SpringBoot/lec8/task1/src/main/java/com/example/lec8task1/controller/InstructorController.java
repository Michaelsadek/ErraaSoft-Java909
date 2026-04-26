package com.example.lec8task1.controller;

import com.example.lec8task1.dto.request.UniversityRequests.CreateInstructorRequest;
import com.example.lec8task1.dto.response.UniversityResponses.CourseSummaryResponse;
import com.example.lec8task1.dto.response.UniversityResponses.InstructorDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.InstructorSummaryResponse;
import com.example.lec8task1.service.InstructorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InstructorSummaryResponse createInstructor(@Valid @RequestBody CreateInstructorRequest request) {
        return instructorService.createInstructor(request);
    }

    @GetMapping
    public List<InstructorSummaryResponse> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/{id}")
    public InstructorDetailsResponse getInstructorById(@PathVariable Long id) {
        return instructorService.getInstructorById(id);
    }

    @GetMapping("/{id}/courses")
    public List<CourseSummaryResponse> getCoursesTaughtByInstructor(@PathVariable Long id) {
        return instructorService.getCoursesTaughtByInstructor(id);
    }
}
