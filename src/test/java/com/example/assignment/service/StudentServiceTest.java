package com.example.assignment.service;

import com.example.assignment.model.Student;
import com.example.assignment.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        Student s1 = new Student("Alice", "alice@example.com");
        Student s2 = new Student("Bob", "bob@example.com");
        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        List<Student> result = studentService.getAll();

        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById() {
        Student s = new Student("Alice", "alice@example.com");
        s.setId(1L);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(s));

        Student result = studentService.getById(1L);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveStudent() {
        Student s = new Student("Alice", "alice@example.com");
        when(studentRepository.save(any(Student.class))).thenReturn(s);

        Student result = studentService.save(s);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        verify(studentRepository, times(1)).save(s);
    }
}
