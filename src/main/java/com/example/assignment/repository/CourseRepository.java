package com.example.assignment.repository;

import com.example.assignment.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c INNER JOIN c.student s ORDER BY s.name, c.title")
    List<Course> findAllCoursesWithStudents();

    List<Course> findByStudentId(Long studentId);
    List<Course> findByTitleContainingIgnoreCase(String title);
    List<Course> findByDescriptionIgnoreCase(String description);
}
