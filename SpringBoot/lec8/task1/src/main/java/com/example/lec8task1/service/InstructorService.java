package com.example.lec8task1.service;

import com.example.lec8task1.dto.request.UniversityRequests.CreateInstructorRequest;
import com.example.lec8task1.dto.response.UniversityResponses.CourseSummaryResponse;
import com.example.lec8task1.dto.response.UniversityResponses.InstructorDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.InstructorSummaryResponse;
import com.example.lec8task1.entity.Instructor;
import com.example.lec8task1.exception.ResourceNotFoundException;
import com.example.lec8task1.repository.InstructorRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Transactional
    public InstructorSummaryResponse createInstructor(CreateInstructorRequest request) {
        Instructor instructor = new Instructor();
        instructor.setName(request.name());
        instructor.setEmail(request.email());
        return UniversityMapper.toInstructorSummary(instructorRepository.save(instructor));
    }

    @Transactional(readOnly = true)
    public List<InstructorSummaryResponse> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .sorted(Comparator.comparing(Instructor::getId))
                .map(UniversityMapper::toInstructorSummary)
                .toList();
    }

    @Transactional(readOnly = true)
    public InstructorDetailsResponse getInstructorById(Long id) {
        return UniversityMapper.toInstructorDetails(findInstructorDetailsById(id));
    }

    @Transactional(readOnly = true)
    public List<CourseSummaryResponse> getCoursesTaughtByInstructor(Long instructorId) {
        Instructor instructor = findInstructorDetailsById(instructorId);
        return instructor.getCourses().stream()
                .sorted(Comparator.comparing(course -> course.getId()))
                .map(UniversityMapper::toCourseSummary)
                .toList();
    }

    private Instructor findInstructorDetailsById(Long id) {
        return instructorRepository.findInstructorDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found with id: " + id));
    }
}
