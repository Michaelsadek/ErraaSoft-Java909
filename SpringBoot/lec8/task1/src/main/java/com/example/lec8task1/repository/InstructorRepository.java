package com.example.lec8task1.repository;

import com.example.lec8task1.entity.Instructor;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @EntityGraph(attributePaths = {"courses", "courses.students"})
    @Query("select i from Instructor i where i.id = :id")
    Optional<Instructor> findInstructorDetailsById(@Param("id") Long id);
}
