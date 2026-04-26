package com.example.lec8task1.repository;

import com.example.lec8task1.entity.Course;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @EntityGraph(attributePaths = {"instructor", "students"})
    @Query("select c from Course c where c.id = :id")
    Optional<Course> findCourseDetailsById(@Param("id") Long id);
}
