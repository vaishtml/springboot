package com.example.assignment.controller;

import com.example.assignment.model.Course;
import com.example.assignment.model.Student;
import com.example.assignment.service.CourseService;
import com.example.assignment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    // -------------------------------------------------------
    // READ: List all courses (Home page)
    // GET /
    // -------------------------------------------------------
    @GetMapping("/")
    public String listCourses(@RequestParam(value = "search", required = false) String search,
                              @RequestParam(value = "description", required = false) String description,
                              Model model) {
        try {
            List<Course> courses;
            if (search != null && !search.trim().isEmpty()) {
                courses = courseService.searchByTitle(search);
                model.addAttribute("pageTitle", "Search Results for '" + search + "'");
            } else if (description != null && !description.trim().isEmpty() && !description.equals("All")) {
                courses = courseService.filterByDescription(description);
                model.addAttribute("pageTitle", description + " Courses");
            } else {
                courses = courseService.getAll();
                model.addAttribute("pageTitle", "All Courses");
            }
            model.addAttribute("courses", courses);
            model.addAttribute("searchKeyword", search);
            model.addAttribute("selectedDescription", description);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Could not load courses: " + e.getMessage());
        }
        return "list";
    }

    // -------------------------------------------------------
    // CREATE: Show the add-course form
    // GET /add
    // -------------------------------------------------------
    @GetMapping("/add")
    public String showAddForm(Model model) {
        try {
            List<Student> students = studentService.getAll();
            model.addAttribute("course", new Course());
            model.addAttribute("students", students);
            model.addAttribute("pageTitle", "Add New Course");
            model.addAttribute("formAction", "/save");
            model.addAttribute("buttonLabel", "Add Course");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Could not load form: " + e.getMessage());
        }
        return "form";
    }

    // -------------------------------------------------------
    // CREATE: Handle form submission and save new course
    // POST /save
    // -------------------------------------------------------
    @PostMapping("/save")
    public String saveCourse(@RequestParam("title") String title,
                             @RequestParam("description") String description,
                             @RequestParam("studentId") Long studentId,
                             RedirectAttributes redirectAttributes) {
        try {
            Student student = studentService.getById(studentId);
            Course course = new Course(title, description, student);
            courseService.save(course);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Course '" + title + "' added successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Integrity violation: This course may already exist or violates a constraint.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error saving course: " + e.getMessage());
        }
        return "redirect:/";
    }

    // -------------------------------------------------------
    // UPDATE: Show the edit form pre-filled with course data
    // GET /edit/{id}
    // -------------------------------------------------------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            Course course = courseService.getById(id);
            List<Student> students = studentService.getAll();
            model.addAttribute("course", course);
            model.addAttribute("students", students);
            model.addAttribute("pageTitle", "Edit Course");
            model.addAttribute("formAction", "/update/" + id);
            model.addAttribute("buttonLabel", "Update Course");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Course not found: " + e.getMessage());
            return "list";
        }
        return "form";
    }

    // -------------------------------------------------------
    // UPDATE: Handle update form submission
    // POST /update/{id}
    // -------------------------------------------------------
    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable Long id,
                               @RequestParam("title") String title,
                               @RequestParam("description") String description,
                               @RequestParam("studentId") Long studentId,
                               RedirectAttributes redirectAttributes) {
        try {
            Student student = studentService.getById(studentId);
            Course updatedCourse = new Course(title, description, student);
            courseService.update(id, updatedCourse);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Course updated successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Integrity violation while updating course. Please check your inputs.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error updating course: " + e.getMessage());
        }
        return "redirect:/";
    }

    // -------------------------------------------------------
    // DELETE: Remove a course by ID
    // GET /delete/{id}
    // -------------------------------------------------------
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Course deleted successfully!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Cannot delete: course is referenced by other records.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error deleting course: " + e.getMessage());
        }
        return "redirect:/";
    }
}
