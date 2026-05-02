package com.example.assignment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Course(String title, String description, Student student) {
        this.title = title;
        this.description = description;
        this.student = student;
    }
}
