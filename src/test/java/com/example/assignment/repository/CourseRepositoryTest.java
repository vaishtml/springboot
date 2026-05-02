package com.example.assignment.repository;

import com.example.assignment.model.Course;
import com.example.assignment.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Student savedStudent;

    @BeforeEach
    void setUp() {
        Student student = new Student("Test Student", "test@student.com");
        savedStudent = studentRepository.save(student);
    }

    @Test
    void testSaveCourse() {
        Course course = new Course("Test Course", "Beginner", savedStudent);
        Course saved = courseRepository.save(course);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindById() {
        Course course = new Course("Test Course", "Beginner", savedStudent);
        Course saved = courseRepository.save(course);
        Optional<Course> found = courseRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Test Course", found.get().getTitle());
    }

    @Test
    void testFindByTitleContainingIgnoreCase() {
        courseRepository.save(new Course("Java Programming", "Beginner", savedStudent));
        courseRepository.save(new Course("Advanced Java", "Advanced", savedStudent));
        
        List<Course> found = courseRepository.findByTitleContainingIgnoreCase("java");
        assertEquals(2, found.size());
    }

    @Test
    void testFindByDescriptionIgnoreCase() {
        courseRepository.save(new Course("Course 1", "Beginner", savedStudent));
        courseRepository.save(new Course("Course 2", "Beginner", savedStudent));
        
        List<Course> found = courseRepository.findByDescriptionIgnoreCase("beginner");
        assertEquals(2, found.size());
    }
}
