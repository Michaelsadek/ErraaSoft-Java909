package com.example.lec8task1.controller;

import com.example.lec8task1.dto.request.UniversityRequests.AssignInstructorToCourseRequest;
import com.example.lec8task1.dto.request.UniversityRequests.CreateCourseRequest;
import com.example.lec8task1.dto.response.UniversityResponses.CourseDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.CourseSummaryResponse;
import com.example.lec8task1.service.CourseService;
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
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseSummaryResponse createCourse(@Valid @RequestBody CreateCourseRequest request) {
        return courseService.createCourse(request);
    }

    @GetMapping
    public List<CourseSummaryResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CourseDetailsResponse getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/{id}/instructor")
    public CourseDetailsResponse assignInstructorToCourse(
            @PathVariable Long id,
            @Valid @RequestBody AssignInstructorToCourseRequest request
    ) {
        return courseService.assignInstructorToCourse(id, request.instructorId());
    }
}
