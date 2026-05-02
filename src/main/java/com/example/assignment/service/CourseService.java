package com.example.assignment.service;

import com.example.assignment.model.Course;
import com.example.assignment.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAll() {
        try {
            return courseRepository.findAllCoursesWithStudents();
        } catch (Exception e) {
            System.err.println("Error fetching all courses: " + e.getMessage());
            throw e;
        }
    }

    public List<Course> searchByTitle(String keyword) {
        return courseRepository.findByTitleContainingIgnoreCase(keyword);
    }

    public List<Course> filterByDescription(String description) {
        return courseRepository.findByDescriptionIgnoreCase(description);
    }

    public Course save(Course course) {
        try {
            return courseRepository.save(course);
        } catch (Exception e) {
            System.err.println("Error saving course: " + e.getMessage());
            throw e;
        }
    }

    public Course getById(Long id) {
        try {
            Optional<Course> optional = courseRepository.findById(id);
            return optional.orElseThrow(() ->
                    new RuntimeException("Course not found with id: " + id));
        } catch (Exception e) {
            System.err.println("Error fetching course by id: " + e.getMessage());
            throw e;
        }
    }

    public void delete(Long id) {
        try {
            Course course = getById(id);
            courseRepository.delete(course);
        } catch (Exception e) {
            System.err.println("Error deleting course: " + e.getMessage());
            throw e;
        }
    }

    public Course update(Long id, Course updatedCourse) {
        try {
            Course existing = getById(id);
            existing.setTitle(updatedCourse.getTitle());
            existing.setDescription(updatedCourse.getDescription());
            existing.setStudent(updatedCourse.getStudent());
            return courseRepository.save(existing);
        } catch (Exception e) {
            System.err.println("Error updating course: " + e.getMessage());
            throw e;
        }
    }
}
