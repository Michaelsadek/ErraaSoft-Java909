package com.example.lec8task1.dto.response;

import java.util.List;

public final class UniversityResponses {

    private UniversityResponses() {
    }

    public record StudentSummaryResponse(
            Long id,
            String name,
            String email
    ) {
    }

    public record InstructorSummaryResponse(
            Long id,
            String name,
            String email
    ) {
    }

    public record CourseSummaryResponse(
            Long id,
            String title,
            String description
    ) {
    }

    public record StudentCourseResponse(
            Long id,
            String title,
            String description,
            InstructorSummaryResponse instructor
    ) {
    }

    public record StudentDetailsResponse(
            Long id,
            String name,
            String email,
            List<StudentCourseResponse> courses
    ) {
    }

    public record CourseDetailsResponse(
            Long id,
            String title,
            String description,
            InstructorSummaryResponse instructor,
            List<StudentSummaryResponse> students
    ) {
    }

    public record InstructorCourseResponse(
            Long id,
            String title,
            String description,
            List<StudentSummaryResponse> students
    ) {
    }

    public record InstructorDetailsResponse(
            Long id,
            String name,
            String email,
            List<InstructorCourseResponse> courses
    ) {
    }
}
