package com.example.lec8task1.repository;

import com.example.lec8task1.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @EntityGraph(attributePaths = {"courses", "courses.instructor"})
    @Query("select s from Student s where s.id = :id")
    Optional<Student> findStudentDetailsById(@Param("id") Long id);
}
