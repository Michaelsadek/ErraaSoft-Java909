package com.example.lec8task1.service;

import com.example.lec8task1.dto.request.UniversityRequests.CreateStudentRequest;
import com.example.lec8task1.dto.response.UniversityResponses.StudentDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.StudentSummaryResponse;
import com.example.lec8task1.entity.Course;
import com.example.lec8task1.entity.Student;
import com.example.lec8task1.exception.ResourceNotFoundException;
import com.example.lec8task1.repository.CourseRepository;
import com.example.lec8task1.repository.StudentRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public StudentSummaryResponse createStudent(CreateStudentRequest request) {
        Student student = new Student();
        student.setName(request.name());
        student.setEmail(request.email());
        return UniversityMapper.toStudentSummary(studentRepository.save(student));
    }

    @Transactional(readOnly = true)
    public List<StudentSummaryResponse> getAllStudents() {
        return studentRepository.findAll().stream()
                .sorted(Comparator.comparing(Student::getId))
                .map(UniversityMapper::toStudentSummary)
                .toList();
    }

    @Transactional(readOnly = true)
    public StudentDetailsResponse getStudentById(Long id) {
        return UniversityMapper.toStudentDetails(findStudentDetailsById(id));
    }

    @Transactional
    public StudentDetailsResponse registerStudentToCourse(Long studentId, Long courseId) {
        Student student = findStudentById(studentId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        student.addCourse(course);
        studentRepository.save(student);

        return UniversityMapper.toStudentDetails(findStudentDetailsById(studentId));
    }

    private Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    private Student findStudentDetailsById(Long id) {
        return studentRepository.findStudentDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }
}
