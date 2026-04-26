package com.example.lec8task1;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.lec8task1.dto.request.UniversityRequests.AssignInstructorToCourseRequest;
import com.example.lec8task1.dto.request.UniversityRequests.CreateCourseRequest;
import com.example.lec8task1.dto.request.UniversityRequests.CreateInstructorRequest;
import com.example.lec8task1.dto.request.UniversityRequests.CreateStudentRequest;
import com.example.lec8task1.dto.request.UniversityRequests.RegisterStudentToCourseRequest;
import com.example.lec8task1.dto.response.UniversityResponses.CourseDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.CourseSummaryResponse;
import com.example.lec8task1.dto.response.UniversityResponses.InstructorDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.InstructorSummaryResponse;
import com.example.lec8task1.dto.response.UniversityResponses.StudentDetailsResponse;
import com.example.lec8task1.dto.response.UniversityResponses.StudentSummaryResponse;
import java.util.List;
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
class UniversityApiIntegrationTest {

    private static final ParameterizedTypeReference<List<StudentSummaryResponse>> STUDENT_LIST_RESPONSE =
            new ParameterizedTypeReference<>() {
            };
    private static final ParameterizedTypeReference<List<CourseSummaryResponse>> COURSE_LIST_RESPONSE =
            new ParameterizedTypeReference<>() {
            };
    private static final ParameterizedTypeReference<List<InstructorSummaryResponse>> INSTRUCTOR_LIST_RESPONSE =
            new ParameterizedTypeReference<>() {
            };

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void universityApisHandleRelationshipsAndDetailedViews() {
        ResponseEntity<InstructorSummaryResponse> instructorResponse = restTemplate.postForEntity(
                "/api/instructors",
                new CreateInstructorRequest("Dr. Sara", "sara@uni.com"),
                InstructorSummaryResponse.class
        );

        assertThat(instructorResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(instructorResponse.getBody()).isNotNull();
        Long instructorId = instructorResponse.getBody().id();

        ResponseEntity<CourseSummaryResponse> courseResponse = restTemplate.postForEntity(
                "/api/courses",
                new CreateCourseRequest("Spring Boot", "Build REST APIs with Spring Boot."),
                CourseSummaryResponse.class
        );

        assertThat(courseResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(courseResponse.getBody()).isNotNull();
        Long courseId = courseResponse.getBody().id();

        ResponseEntity<StudentSummaryResponse> firstStudentResponse = restTemplate.postForEntity(
                "/api/students",
                new CreateStudentRequest("Ahmed Ali", "ahmed@uni.com"),
                StudentSummaryResponse.class
        );
        ResponseEntity<StudentSummaryResponse> secondStudentResponse = restTemplate.postForEntity(
                "/api/students",
                new CreateStudentRequest("Mona Hassan", "mona@uni.com"),
                StudentSummaryResponse.class
        );

        assertThat(firstStudentResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(secondStudentResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(firstStudentResponse.getBody()).isNotNull();
        assertThat(secondStudentResponse.getBody()).isNotNull();

        Long firstStudentId = firstStudentResponse.getBody().id();
        Long secondStudentId = secondStudentResponse.getBody().id();

        ResponseEntity<CourseDetailsResponse> assignedCourseResponse = restTemplate.exchange(
                "/api/courses/" + courseId + "/instructor",
                HttpMethod.PUT,
                new HttpEntity<>(new AssignInstructorToCourseRequest(instructorId)),
                CourseDetailsResponse.class
        );

        assertThat(assignedCourseResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(assignedCourseResponse.getBody()).isNotNull();
        assertThat(assignedCourseResponse.getBody().instructor()).isNotNull();
        assertThat(assignedCourseResponse.getBody().instructor().name()).isEqualTo("Dr. Sara");

        ResponseEntity<StudentDetailsResponse> firstRegistrationResponse = restTemplate.exchange(
                "/api/students/" + firstStudentId + "/courses",
                HttpMethod.PUT,
                new HttpEntity<>(new RegisterStudentToCourseRequest(courseId)),
                StudentDetailsResponse.class
        );
        ResponseEntity<StudentDetailsResponse> secondRegistrationResponse = restTemplate.exchange(
                "/api/students/" + secondStudentId + "/courses",
                HttpMethod.PUT,
                new HttpEntity<>(new RegisterStudentToCourseRequest(courseId)),
                StudentDetailsResponse.class
        );

        assertThat(firstRegistrationResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(secondRegistrationResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<List<StudentSummaryResponse>> allStudentsResponse = restTemplate.exchange(
                "/api/students",
                HttpMethod.GET,
                null,
                STUDENT_LIST_RESPONSE
        );
        ResponseEntity<List<CourseSummaryResponse>> allCoursesResponse = restTemplate.exchange(
                "/api/courses",
                HttpMethod.GET,
                null,
                COURSE_LIST_RESPONSE
        );
        ResponseEntity<List<InstructorSummaryResponse>> allInstructorsResponse = restTemplate.exchange(
                "/api/instructors",
                HttpMethod.GET,
                null,
                INSTRUCTOR_LIST_RESPONSE
        );

        assertThat(allStudentsResponse.getBody()).hasSize(2);
        assertThat(allCoursesResponse.getBody()).hasSize(1);
        assertThat(allInstructorsResponse.getBody()).hasSize(1);

        ResponseEntity<StudentDetailsResponse> studentDetailsResponse = restTemplate.getForEntity(
                "/api/students/" + firstStudentId,
                StudentDetailsResponse.class
        );
        ResponseEntity<CourseDetailsResponse> courseDetailsResponse = restTemplate.getForEntity(
                "/api/courses/" + courseId,
                CourseDetailsResponse.class
        );
        ResponseEntity<InstructorDetailsResponse> instructorDetailsResponse = restTemplate.getForEntity(
                "/api/instructors/" + instructorId,
                InstructorDetailsResponse.class
        );
        ResponseEntity<List<CourseSummaryResponse>> instructorCoursesResponse = restTemplate.exchange(
                "/api/instructors/" + instructorId + "/courses",
                HttpMethod.GET,
                null,
                COURSE_LIST_RESPONSE
        );

        assertThat(studentDetailsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentDetailsResponse.getBody()).isNotNull();
        assertThat(studentDetailsResponse.getBody().courses()).hasSize(1);
        assertThat(studentDetailsResponse.getBody().courses().get(0).title()).isEqualTo("Spring Boot");
        assertThat(studentDetailsResponse.getBody().courses().get(0).instructor()).isNotNull();
        assertThat(studentDetailsResponse.getBody().courses().get(0).instructor().name()).isEqualTo("Dr. Sara");

        assertThat(courseDetailsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(courseDetailsResponse.getBody()).isNotNull();
        assertThat(courseDetailsResponse.getBody().students()).extracting(StudentSummaryResponse::name)
                .containsExactlyInAnyOrder("Ahmed Ali", "Mona Hassan");

        assertThat(instructorDetailsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(instructorDetailsResponse.getBody()).isNotNull();
        assertThat(instructorDetailsResponse.getBody().courses()).hasSize(1);
        assertThat(instructorDetailsResponse.getBody().courses().get(0).students()).extracting(StudentSummaryResponse::name)
                .containsExactlyInAnyOrder("Ahmed Ali", "Mona Hassan");

        assertThat(instructorCoursesResponse.getBody()).hasSize(1);
        assertThat(instructorCoursesResponse.getBody().get(0).title()).isEqualTo("Spring Boot");
    }
}
