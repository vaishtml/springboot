package com.example.assignment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Student name is required")
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @NotBlank(message = "Student email is required")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // One Student can have many Courses
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude // Avoid infinite recursion in toString()
    private List<Course> courses;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
