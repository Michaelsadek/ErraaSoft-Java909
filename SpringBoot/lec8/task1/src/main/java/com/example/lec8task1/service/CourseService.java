package com.example.lec8task1.service;

import com.example.lec8task1.dto.request.UniversityRequests.CreateCourseRequest;
import com.example.lec8task1.dto.response.UniversityResponses.CourseDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.CourseSummaryResponse;
import com.example.lec8task1.entity.Course;
import com.example.lec8task1.entity.Instructor;
import com.example.lec8task1.exception.ResourceNotFoundException;
import com.example.lec8task1.repository.CourseRepository;
import com.example.lec8task1.repository.InstructorRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public CourseService(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    @Transactional
    public CourseSummaryResponse createCourse(CreateCourseRequest request) {
        Course course = new Course();
        course.setTitle(request.title());
        course.setDescription(request.description());
        return UniversityMapper.toCourseSummary(courseRepository.save(course));
    }

    @Transactional(readOnly = true)
    public List<CourseSummaryResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .sorted(Comparator.comparing(Course::getId))
                .map(UniversityMapper::toCourseSummary)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseDetailsResponse getCourseById(Long id) {
        return UniversityMapper.toCourseDetails(findCourseDetailsById(id));
    }

    @Transactional
    public CourseDetailsResponse assignInstructorToCourse(Long courseId, Long instructorId) {
        Course course = findCourseById(courseId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + instructorId));

        course.setInstructor(instructor);
        courseRepository.save(course);

        return UniversityMapper.toCourseDetails(findCourseDetailsById(courseId));
    }

    private Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    private Course findCourseDetailsById(Long id) {
        return courseRepository.findCourseDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }
}
