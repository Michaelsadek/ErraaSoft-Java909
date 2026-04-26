package com.example.lec8task1.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class UniversityRequests {

    private UniversityRequests() {
    }

    public record CreateStudentRequest(
            @NotBlank(message = "Student name is required.")
            String name,
            @NotBlank(message = "Student email is required.")
            @Email(message = "Student email must be valid.")
            String email
    ) {
    }

    public record CreateCourseRequest(
            @NotBlank(message = "Course title is required.")
            String title,
            @NotBlank(message = "Course description is required.")
            String description
    ) {
    }

    public record CreateInstructorRequest(
            @NotBlank(message = "Instructor name is required.")
            String name,
            @NotBlank(message = "Instructor email is required.")
            @Email(message = "Instructor email must be valid.")
            String email
    ) {
    }

    public record RegisterStudentToCourseRequest(
            @NotNull(message = "Course id is required.")
            Long courseId
    ) {
    }

    public record AssignInstructorToCourseRequest(
            @NotNull(message = "Instructor id is required.")
            Long instructorId
    ) {
    }
}
