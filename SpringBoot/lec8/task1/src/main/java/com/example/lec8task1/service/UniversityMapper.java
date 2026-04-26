package com.example.lec8task1.service;

import com.example.lec8task1.dto.response.UniversityResponses.CourseDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.CourseSummaryResponse;
import com.example.lec8task1.dto.response.UniversityResponses.InstructorCourseResponse;
import com.example.lec8task1.dto.response.UniversityResponses.InstructorDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.InstructorSummaryResponse;
import com.example.lec8task1.dto.response.UniversityResponses.StudentCourseResponse;
import com.example.lec8task1.dto.response.UniversityResponses.StudentDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.StudentSummaryResponse;
import com.example.lec8task1.entity.Course;
import com.example.lec8task1.entity.Instructor;
import com.example.lec8task1.entity.Student;
import java.util.Comparator;

public final class UniversityMapper {

    private UniversityMapper() {
    }

    public static StudentSummaryResponse toStudentSummary(Student student) {
        return new StudentSummaryResponse(student.getId(), student.getName(), student.getEmail());
    }

    public static InstructorSummaryResponse toInstructorSummary(Instructor instructor) {
        if (instructor == null) {
            return null;
        }

        return new InstructorSummaryResponse(instructor.getId(), instructor.getName(), instructor.getEmail());
    }

    public static CourseSummaryResponse toCourseSummary(Course course) {
        return new CourseSummaryResponse(course.getId(), course.getTitle(), course.getDescription());
    }

    public static StudentCourseResponse toStudentCourseResponse(Course course) {
        return new StudentCourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                toInstructorSummary(course.getInstructor())
        );
    }

    public static StudentDetailsResponse toStudentDetails(Student student) {
        return new StudentDetailsResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getCourses().stream()
                        .sorted(Comparator.comparing(Course::getId))
                        .map(UniversityMapper::toStudentCourseResponse)
                        .toList()
        );
    }

    public static CourseDetailsResponse toCourseDetails(Course course) {
        return new CourseDetailsResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                toInstructorSummary(course.getInstructor()),
                course.getStudents().stream()
                        .sorted(Comparator.comparing(Student::getId))
                        .map(UniversityMapper::toStudentSummary)
                        .toList()
        );
    }

    public static InstructorCourseResponse toInstructorCourseResponse(Course course) {
        return new InstructorCourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getStudents().stream()
                        .sorted(Comparator.comparing(Student::getId))
                        .map(UniversityMapper::toStudentSummary)
                        .toList()
        );
    }

    public static InstructorDetailsResponse toInstructorDetails(Instructor instructor) {
        return new InstructorDetailsResponse(
                instructor.getId(),
                instructor.getName(),
                instructor.getEmail(),
                instructor.getCourses().stream()
                        .sorted(Comparator.comparing(Course::getId))
                        .map(UniversityMapper::toInstructorCourseResponse)
                        .toList()
        );
    }
}
