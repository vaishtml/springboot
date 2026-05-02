package com.example.assignment.service;

import com.example.assignment.model.Student;
import com.example.assignment.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAll() {
        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            System.err.println("Error fetching all students: " + e.getMessage());
            throw e;
        }
    }

    public Student save(Student student) {
        try {
            return studentRepository.save(student);
        } catch (Exception e) {
            System.err.println("Error saving student: " + e.getMessage());
            throw e;
        }
    }

    public Student getById(Long id) {
        try {
            Optional<Student> optional = studentRepository.findById(id);
            return optional.orElseThrow(() ->
                    new RuntimeException("Student not found with id: " + id));
        } catch (Exception e) {
            System.err.println("Error fetching student by id: " + e.getMessage());
            throw e;
        }
    }

    public Student update(Long id, Student updatedStudent) {
        try {
            Student existing = getById(id);
            existing.setName(updatedStudent.getName());
            existing.setEmail(updatedStudent.getEmail());
            return studentRepository.save(existing);
        } catch (Exception e) {
            System.err.println("Error updating student: " + e.getMessage());
            throw e;
        }
    }
}
