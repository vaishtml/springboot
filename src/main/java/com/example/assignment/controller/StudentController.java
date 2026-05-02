package com.example.assignment.controller;

import com.example.assignment.model.Student;
import com.example.assignment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // -------------------------------------------------------
    // READ: List all students
    // GET /students
    // -------------------------------------------------------
    @GetMapping
    public String listStudents(Model model) {
        try {
            List<Student> students = studentService.getAll();
            model.addAttribute("students", students);
            model.addAttribute("pageTitle", "All Students");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Could not load students: " + e.getMessage());
        }
        return "student-list";
    }

    // -------------------------------------------------------
    // CREATE: Show the add-student form
    // GET /students/add
    // -------------------------------------------------------
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("pageTitle", "Add New Student");
        model.addAttribute("formAction", "/students/save");
        model.addAttribute("buttonLabel", "Add Student");
        return "student-form";
    }

    // -------------------------------------------------------
    // CREATE: Save new student
    // POST /students/save
    // -------------------------------------------------------
    @PostMapping("/save")
    public String saveStudent(@RequestParam("name") String name,
                              @RequestParam("email") String email,
                              RedirectAttributes redirectAttributes) {
        try {
            Student student = new Student(name.trim(), email.trim());
            studentService.save(student);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Student '" + name + "' added successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Integrity violation: A student with this email may already exist.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error saving student: " + e.getMessage());
        }
        return "redirect:/students";
    }

    // -------------------------------------------------------
    // UPDATE: Show edit form for a student
    // GET /students/edit/{id}
    // -------------------------------------------------------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Student student = studentService.getById(id);
            model.addAttribute("student", student);
            model.addAttribute("pageTitle", "Edit Student");
            model.addAttribute("formAction", "/students/update/" + id);
            model.addAttribute("buttonLabel", "Update Student");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Student not found: " + e.getMessage());
        }
        return "student-form";
    }

    // -------------------------------------------------------
    // UPDATE: Handle update form submission
    // POST /students/update/{id}
    // -------------------------------------------------------
    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @RequestParam("name") String name,
                                @RequestParam("email") String email,
                                RedirectAttributes redirectAttributes) {
        try {
            Student updatedStudent = new Student(name.trim(), email.trim());
            studentService.update(id, updatedStudent);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Student updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Integrity violation while updating student. Please check your input.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error updating student: " + e.getMessage());
        }
        return "redirect:/students";
    }
}
